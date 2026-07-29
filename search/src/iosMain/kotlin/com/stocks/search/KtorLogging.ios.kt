package com.stocks.search.repo.api

import io.ktor.client.plugins.logging.Logger

actual fun createKtorLogger(): Logger = object : Logger {
    override fun log(message: String) {
        println(message)
    }
}
