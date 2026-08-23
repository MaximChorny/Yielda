package com.stocks.search.repo.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.stocks.search.db.SearchDatabase

actual class SearchDatabaseDriverFactory(
    private val context: Context,
) {
    actual fun createDriver(): SqlDriver = AndroidSqliteDriver(
        schema = SearchDatabase.Schema,
        context = context,
        name = "search.db",
    )
}
