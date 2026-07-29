package com.stocks.yielda

import android.app.Application
import com.stocks.search.di.initKoin
import com.stocks.yielda.di.appModule
import org.koin.android.ext.koin.androidContext

class YieldaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@YieldaApp)
            modules(appModule)
        }
    }
}
