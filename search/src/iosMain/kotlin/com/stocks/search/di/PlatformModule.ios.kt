package com.stocks.search.di

import com.stocks.search.BuildKonfig
import com.stocks.search.repo.api.createFinnhubHttpClient
import com.stocks.search.repo.api.createTwelveDataHttpClient
import com.stocks.search.repo.db.SearchDatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {
    single(named(HttpClientQualifier.Finnhub)) {
        createFinnhubHttpClient(BuildKonfig.FINNHUB_API_KEY)
    }
    single(named(HttpClientQualifier.TwelveData)) {
        createTwelveDataHttpClient(BuildKonfig.TWELVE_DATA_API_KEY)
    }
    single { SearchDatabaseDriverFactory() }
}
