package com.example.app.data.reminders


import com.example.app.data.common.db.LocalDataSource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class RemindersRepository(
        private val localDataSource: LocalDataSource
    ) {
    val reminders: List<Reminder>
        get() = localDataSource.getAllTasks().map { it.map() }


    @OptIn(ExperimentalUuidApi::class)
    fun createReminder(title: String) {
        localDataSource.insertTask(title)
    }

        fun markReminder(id: Long, isCompleted: Boolean) {
            if (isCompleted) {
                localDataSource.markTaskCompleted(id)
            } else {
                localDataSource.markTaskPending(id)
            }
        }

        fun deleteReminder(id: Long) {
            localDataSource.deleteTask(id)
        }
}