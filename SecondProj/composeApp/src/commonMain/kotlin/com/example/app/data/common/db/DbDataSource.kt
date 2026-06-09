package com.example.app.data.common.db


import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import com.example.app.AppDatabase
import com.example.app.Task

class DbDataSource(
    private val db: AppDatabase
) : LocalDataSource {

    override fun insertTask(description: String) {
        db.tasksQueries.insertTask(task_desc = description)
    }

    override fun getAllTasks(): List<Task> {
        return db.tasksQueries.selectAllTasks().executeAsList()
    }

    override fun markTaskCompleted(id: Long) {
        db.tasksQueries.markTaskCompleted(id = id)
    }

    override fun markTaskPending(id: Long) {
        db.tasksQueries.markTaskPending(id = id)
    }

    override fun deleteTask(id: Long) {
        db.tasksQueries.deleteTaskById(id = id)
    }
}