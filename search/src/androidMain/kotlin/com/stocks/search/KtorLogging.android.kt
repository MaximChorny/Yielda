package com.stocks.search.repo.api

import android.util.Log
import io.ktor.client.plugins.logging.Logger

actual fun createKtorLogger(): Logger = object : Logger {
    override fun log(message: String) {
        Log.d("FinnhubHttp", message)
    }
}
