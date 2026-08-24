package com.stocks.search.repo.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual fun createFinnhubHttpClient(apiKey: String): HttpClient = HttpClient(Darwin) {
    installApiLogging()

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = false
                isLenient = true
            }
        )
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 15_000
        connectTimeoutMillis = 15_000
        socketTimeoutMillis = 15_000
    }

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "finnhub.io"
            path("api", "v1/")
            parameters.append("token", apiKey)
        }
    }
}
