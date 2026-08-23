package com.stocks.search.di

import com.stocks.search.SearchViewModel
import com.stocks.search.db.SearchDatabase
import com.stocks.search.repo.FinnhubSearchRepository
import com.stocks.search.repo.RecentSearchRepository
import com.stocks.search.repo.db.SearchDatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

val searchModule: Module = module {
    single { SearchDatabase(get<SearchDatabaseDriverFactory>().createDriver()) }
    single { FinnhubSearchRepository(get(), get()) }
    single { RecentSearchRepository(get()) }
    factory { SearchViewModel(get(), get()) }
}
