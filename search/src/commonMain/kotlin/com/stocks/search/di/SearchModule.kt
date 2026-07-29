package com.stocks.search.di

import com.stocks.search.SearchViewModel
import com.stocks.search.repo.FinnhubSearchRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val searchModule: Module = module {
    single { FinnhubSearchRepository(get()) }
    factory { SearchViewModel(get()) }
}
