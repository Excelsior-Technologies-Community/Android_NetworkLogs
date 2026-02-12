package com.ext.android_networklogs

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ext.networklogger.NetworkLogger
import com.ext.networklogger.NetworkLoggerConfig
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import okhttp3.Callback
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        NetworkLoggerConfig.enabled = true
        testNetworkLogger()
    }
    private fun testNetworkLogger() {

        // ✅ Create client from your library
        val client = NetworkLogger.createClient()

        // ✅ Test API request
        val request = Request.Builder()
            .url("https://jsonplaceholder.typicode.com/posts/1")
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("APP_TEST", "Request Failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("APP_TEST", "Response Success")

                Log.d("APP_TEST", response.body?.string() ?: "No Body")
            }
        })
    }
}