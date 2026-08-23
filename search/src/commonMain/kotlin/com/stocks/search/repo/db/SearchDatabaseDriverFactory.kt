package com.stocks.search.repo.db

import app.cash.sqldelight.db.SqlDriver

expect class SearchDatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
