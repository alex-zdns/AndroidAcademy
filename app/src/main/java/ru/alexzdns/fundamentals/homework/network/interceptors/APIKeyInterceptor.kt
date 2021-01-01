package ru.alexzdns.fundamentals.homework.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor: Interceptor {
    private val API_KEY = "79827c34a6d2d04b58c4b364e37255a9"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val request = original.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}