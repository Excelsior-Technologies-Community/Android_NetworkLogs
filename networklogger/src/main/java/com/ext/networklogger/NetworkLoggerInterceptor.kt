package com.ext.networklogger

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class NetworkLoggerInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // ✅ Disable logging completely
        if (!NetworkLoggerConfig.enabled) {
            return chain.proceed(chain.request())
        }

        val request = chain.request()
        val startTime = System.nanoTime()

        // ✅ BASIC Logs
        Log.d("NetworkLogger", "➡️ Request URL: ${request.url}")
        Log.d("NetworkLogger", "➡️ Method: ${request.method}")

        // ✅ HEADERS Logs
        if (NetworkLoggerConfig.level == LogLevel.HEADERS ||
            NetworkLoggerConfig.level == LogLevel.BODY
        ) {
            Log.d("NetworkLogger", "➡️ Headers: ${request.headers}")
        }

        // ✅ BODY Logs (Request Body)
        if (NetworkLoggerConfig.level == LogLevel.BODY) {
            request.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)

                val requestBodyString = buffer.readUtf8()
                Log.d(
                    "NetworkLogger",
                    "➡️ Request Body:\n${prettyPrintJson(requestBodyString)}"
                )
            }
        }

        // ✅ Proceed Request
        val response = chain.proceed(request)

        // ✅ Time Taken
        val endTime = System.nanoTime()
        val durationMs =
            TimeUnit.NANOSECONDS.toMillis(endTime - startTime)

        // ✅ Response Body Read
        val responseBody = response.body
        val responseBodyString = responseBody?.string()

        // ✅ BASIC Response Logs
        Log.d("NetworkLogger", "⬅️ Response Code: ${response.code}")
        Log.d("NetworkLogger", "⬅️ Time Taken: ${durationMs}ms")

        // ✅ BODY Logs (Response Body)
        if (NetworkLoggerConfig.level == LogLevel.BODY) {
            Log.d(
                "NetworkLogger",
                "⬅️ Response Body:\n${prettyPrintJson(responseBodyString)}"
            )
        }

        // ✅ Rebuild Response
        return response.newBuilder()
            .body(
                responseBodyString?.toResponseBody(responseBody?.contentType())
            )
            .build()
    }

    // ✅ JSON Pretty Print Helper
    private fun prettyPrintJson(body: String?): String {
        if (body.isNullOrEmpty()) return "Empty Body"

        return try {
            when {
                body.trim().startsWith("{") -> JSONObject(body).toString(4)
                body.trim().startsWith("[") -> JSONArray(body).toString(4)
                else -> body
            }
        } catch (e: Exception) {
            body
        }
    }
}
