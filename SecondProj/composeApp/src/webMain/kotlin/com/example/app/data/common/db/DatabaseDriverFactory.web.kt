package com.example.app.data.common.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlPreparedStatement

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
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
                return QueryResult.Value(0L)
            }

            override fun <R> executeQuery(
                identifier: Int?,
                sql: String,
                mapper: (SqlCursor) -> QueryResult<R>,
                parameters: Int,
                binders: (SqlPreparedStatement.() -> Unit)?
            ): QueryResult<R> {
                val emptyCursor = object : SqlCursor {
                    override fun next(): QueryResult<Boolean> = QueryResult.Value(false)
                    override fun getString(index: Int): String? = null
                    override fun getLong(index: Int): Long? = null
                    override fun getBytes(index: Int): ByteArray? = null
                    override fun getDouble(index: Int): Double? = null
                    override fun getBoolean(index: Int): Boolean? = null
                }
                return mapper(emptyCursor)
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