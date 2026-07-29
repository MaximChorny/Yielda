package com.stocks.search.repo.api

import io.ktor.client.HttpClient

expect fun createFinnhubHttpClient(apiKey: String): HttpClient
