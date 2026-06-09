package com.example.app.data.common.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.app.AppDatabase
import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {

    actual fun create(): SqlDriver {
        val dbPath = "${System.getProperty("user.home")}/.AppDatabase/AppDatabase.db"
        File(dbPath).parentFile?.mkdirs()
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$dbPath")
        if (!File(dbPath).exists()) {
            AppDatabase.Schema.create(driver)
        }
        return driver
    }
}