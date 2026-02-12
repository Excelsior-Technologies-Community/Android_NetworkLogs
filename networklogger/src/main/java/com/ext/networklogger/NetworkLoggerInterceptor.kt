package com.ext.networklogger

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.util.concurrent.TimeUnit

class NetworkLoggerInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val startTime = System.nanoTime()

        Log.d("NetworkLogger", "➡️ Request URL: ${request.url}")
        Log.d("NetworkLogger", "➡️ Method: ${request.method}")
        Log.d("NetworkLogger", "➡️ Headers: ${request.headers}")

        // Request Body
        request.body?.let { body ->
            val buffer = Buffer()
            body.writeTo(buffer)
            Log.d("NetworkLogger", "➡️ Body: ${buffer.readUtf8()}")
        }

        // Proceed request
        val response = chain.proceed(request)

        val endTime = System.nanoTime()
        val durationMs =
            TimeUnit.NANOSECONDS.toMillis(endTime - startTime)

        Log.d("NetworkLogger", "⬅️ Response Code: ${response.code}")
        Log.d("NetworkLogger", "⬅️ Time Taken: ${durationMs}ms")

        return response
    }
}
