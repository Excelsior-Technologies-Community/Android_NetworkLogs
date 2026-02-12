package com.ext.networklogger

import okhttp3.OkHttpClient

object NetworkLogger {

    fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkLoggerInterceptor())
            .build()
    }
}
