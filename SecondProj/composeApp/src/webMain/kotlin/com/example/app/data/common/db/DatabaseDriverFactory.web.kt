package com.example.app.data.common.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlPreparedStatement
import kotlinx.browser.window

private data class FakeTask(
    val id: Long,
    val taskDesc: String,
    var isCompleted: Long
)

private val tasksList = mutableListOf<FakeTask>()
private var nextId = 1L
private var isLoaded = false

private fun escape(str: String): String {
    return str.replace("%", "%25")
              .replace("|", "%7C")
              .replace("\n", "%0A")
              .replace("\r", "%0D")
}

private fun unescape(str: String): String {
    return str.replace("%7C", "|")
              .replace("%0A", "\n")
              .replace("%0D", "\r")
              .replace("%25", "%")
}

private fun saveTasks() {
    try {
        val serialized = tasksList.joinToString("\n") { task ->
            "${task.id}|${task.isCompleted}|${escape(task.taskDesc)}"
        }
        window.localStorage.setItem("tasks_db_cache", serialized)
    } catch (e: Exception) {
        // Fallback
    }
}

private fun loadTasks() {
    try {
        val serialized = window.localStorage.getItem("tasks_db_cache")
        if (!serialized.isNullOrEmpty()) {
            tasksList.clear()
            serialized.split("\n").forEach { line ->
                val parts = line.split("|")
                if (parts.size >= 3) {
                    val id = parts[0].toLongOrNull() ?: return@forEach
                    val isCompleted = parts[1].toLongOrNull() ?: return@forEach
                    val desc = unescape(parts[2])
                    tasksList.add(FakeTask(id, desc, isCompleted))
                }
            }
            nextId = (tasksList.maxOfOrNull { it.id } ?: 0L) + 1L
        }
    } catch (e: Exception) {
        // Fallback
    }
}

private fun ensureLoaded() {
    if (!isLoaded) {
        isLoaded = true
        loadTasks()
    }
}

private class WebPreparedStatement : SqlPreparedStatement {
    val boundStrings = mutableMapOf<Int, String>()
    val boundLongs = mutableMapOf<Int, Long>()

    override fun bindString(index: Int, value: String?) {
        if (value != null) boundStrings[index] = value
    }

    override fun bindLong(index: Int, value: Long?) {
        if (value != null) boundLongs[index] = value
    }

    override fun bindBytes(index: Int, value: ByteArray?) {}
    override fun bindDouble(index: Int, value: Double?) {}
    override fun bindBoolean(index: Int, value: Boolean?) {}
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        ensureLoaded()
        return object : SqlDriver {
            override fun addListener(vararg queryKeys: String, listener: app.cash.sqldelight.Query.Listener) {}
            override fun removeListener(vararg queryKeys: String, listener: app.cash.sqldelight.Query.Listener) {}
            override fun notifyListeners(vararg queryKeys: String) {}

            override fun execute(
                identifier: Int?,
                sql: String,
                parameters: Int,
                binders: (SqlPreparedStatement.() -> Unit)?
            ): QueryResult<Long> {
                val statement = WebPreparedStatement()
                binders?.invoke(statement)

                when {
                    sql.contains("INSERT INTO Task", ignoreCase = true) -> {
                        val desc = statement.boundStrings.values.firstOrNull() ?: "Task"
                        tasksList.add(FakeTask(nextId++, desc, 0L))
                    }
                    sql.contains("is_completed = 1", ignoreCase = true) -> {
                        val id = statement.boundLongs.values.firstOrNull() ?: 0L
                        tasksList.find { it.id == id }?.isCompleted = 1L
                    }
                    sql.contains("is_completed = 0", ignoreCase = true) -> {
                        val id = statement.boundLongs.values.firstOrNull() ?: 0L
                        tasksList.find { it.id == id }?.isCompleted = 0L
                    }
                    sql.contains("DELETE FROM Task WHERE id = ?", ignoreCase = true) -> {
                        val id = statement.boundLongs.values.firstOrNull() ?: 0L
                        tasksList.removeAll { it.id == id }
                    }
                    sql.contains("DELETE FROM Task", ignoreCase = true) -> {
                        tasksList.clear()
                    }
                }
                saveTasks()
                return QueryResult.Value(1L)
            }

            override fun <R> executeQuery(
                identifier: Int?,
                sql: String,
                mapper: (SqlCursor) -> QueryResult<R>,
                parameters: Int,
                binders: (SqlPreparedStatement.() -> Unit)?
            ): QueryResult<R> {
                ensureLoaded()
                val list = tasksList.toList()
                var index = -1

                val cursor = object : SqlCursor {
                    override fun next(): QueryResult<Boolean> {
                        index++
                        return QueryResult.Value(index < list.size)
                    }

                    override fun getString(columnIndex: Int): String? {
                        val task = list.getOrNull(index) ?: return null
                        return if (columnIndex == 1) task.taskDesc else null
                    }

                    override fun getLong(columnIndex: Int): Long? {
                        val task = list.getOrNull(index) ?: return null
                        return when (columnIndex) {
                            0 -> task.id
                            2 -> task.isCompleted
                            else -> null
                        }
                    }

                    override fun getBytes(columnIndex: Int): ByteArray? = null
                    override fun getDouble(columnIndex: Int): Double? = null
                    override fun getBoolean(columnIndex: Int): Boolean? = null
                }
                return mapper(cursor)
            }

            override fun newTransaction(): QueryResult<app.cash.sqldelight.Transacter.Transaction> {
                val dummyTransaction = object : app.cash.sqldelight.Transacter.Transaction() {
                    override val enclosingTransaction: app.cash.sqldelight.Transacter.Transaction? = null
                    override fun endTransaction(successful: Boolean): QueryResult<Unit> = QueryResult.Value(Unit)
                }
                return QueryResult.Value(dummyTransaction)
            }

            override fun currentTransaction(): app.cash.sqldelight.Transacter.Transaction? = null

            override fun close() {}
        }
    }
}