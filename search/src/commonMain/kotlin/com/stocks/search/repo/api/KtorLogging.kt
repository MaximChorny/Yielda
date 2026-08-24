package com.stocks.search.repo.api

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

expect fun createKtorLogger(): Logger

fun HttpClientConfig<*>.installApiLogging() {
    install(Logging) {
        logger = createKtorLogger()
        level = LogLevel.ALL
    }
}
