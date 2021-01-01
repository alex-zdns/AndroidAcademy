package ru.alexzdns.fundamentals.homework.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class LanguagesInterceptor(val languageCode: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("language", languageCode)
            .build()

        val request = original.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}