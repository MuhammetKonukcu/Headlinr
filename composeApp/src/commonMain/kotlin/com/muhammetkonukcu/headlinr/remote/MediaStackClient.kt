package com.muhammetkonukcu.headlinr.remote

import com.muhammetkonukcu.headlinr.remote.response.MediaStackResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess

class MediaStackClient(
    private val httpClient: HttpClient,
    private val apiKey: String,
    private val defaultRegion: String
) {
    private val baseUrl = "api.mediastack.com"

    suspend fun getLatestNews(
        categories: List<String> = emptyList(),
        limit: Int = 20,
        offset: Int = 0
    ): MediaStackResponse = sendRequest(
        path = "v1/news",
        extraParams = buildMap {
            put("countries", defaultRegion)
            put("limit", limit.toString())
            put("offset", offset.toString())
            if (categories.isNotEmpty()) put("categories", categories.joinToString(","))
        }
    ).body()

    suspend fun searchNews(
        keywords: String,
        categories: List<String> = emptyList(),
        limit: Int = 20,
        offset: Int = 0
    ): MediaStackResponse = sendRequest(
        path = "v1/news",
        extraParams = buildMap {
            put("keywords", keywords)
            put("countries", defaultRegion)
            put("limit", limit.toString())
            put("offset", offset.toString())
            if (categories.isNotEmpty()) put("categories", categories.joinToString(","))
        }
    ).body()

    private suspend fun sendRequest(
        path: String,
        extraParams: Map<String, String> = emptyMap()
    ): HttpResponse {
        val call = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl
                encodedPath = path
                parameter("access_key", apiKey)
                extraParams.forEach { (k, v) -> parameter(k, v) }
            }
        }
        if (!call.status.isSuccess()) {
            println("MediaStack '$path' error: HTTP ${call.status.value}")
            throw RuntimeException("MediaStack '$path' error: HTTP ${call.status.value}")
        }
        return call
    }
}