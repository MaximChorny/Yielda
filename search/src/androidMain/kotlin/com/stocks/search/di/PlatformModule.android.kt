package com.stocks.search.di

import com.stocks.search.BuildKonfig
import com.stocks.search.repo.api.createFinnhubHttpClient
import com.stocks.search.repo.db.SearchDatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { createFinnhubHttpClient(BuildKonfig.FINNHUB_API_KEY) }
    single { SearchDatabaseDriverFactory(androidContext()) }
}
