package com.stocks.search.repo.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.stocks.search.db.SearchDatabase

actual class SearchDatabaseDriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(
        schema = SearchDatabase.Schema,
        name = "search.db",
    )
}
