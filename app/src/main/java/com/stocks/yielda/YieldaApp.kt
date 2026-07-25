package com.stocks.yielda

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YieldaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YieldaApp)
            modules(
                appModule,
//                searchModule,
            )
        }
    }
}
