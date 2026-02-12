## NetworkLogger - Android Kotlin Network Logging Library
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green)](LICENSE)
[![API](https://img.shields.io/badge/API-24%2B-orange)](#)

---

A lightweight and developer-friendly **OkHttp Network Logging Interceptor** for Android, built in Kotlin.

NetworkLogger helps you easily debug API calls by logging:

- Request URL, method, headers
- Request body (JSON formatted)
- Response body (JSON formatted)
- Response code and time taken
- Log control with enable/disable switch
- Log levels (BASIC / HEADERS / BODY)

---

## Features

✅ Request Logging (URL, Method)  
✅ Response Logging (Code, Time Taken)  
✅ Full Request + Response Body Logging  
✅ JSON Pretty Print Support  
✅ Enable/Disable Logging Anytime  
✅ Log Levels Support  
✅ Easy Integration with OkHttp & Retrofit  

---

## Installation (JitPack)

### 1️⃣ Add JitPack to your **root `settings.gradle` or `build.gradle`**

```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
### Add Dependency
```
dependencies {
	        implementation 'com.github.Excelsior-Technologies-Community:Android_NetworkLogs:1.0.0'
	}
```

---

### Usage

**Enable Network Logging**
```kotlin
NetworkLoggerConfig.enabled = true
```

To disable logs:
```kotlin
NetworkLoggerConfig.enabled = false
```


**Select Logging Level**

NetworkLogger provides 3 logging modes:

Level	Logs

- BASIC	URL + Method + Response Code + Time
- HEADERS	BASIC + Request Headers
- BODY	HEADERS + Full Request/Response Body

Set log level like this:
```kotlin
NetworkLoggerConfig.level = LogLevel.BASIC
```

**Retrofit Integration**

Use NetworkLogger with Retrofit:
```kotlin
val client = OkHttpClient.Builder()
    .addInterceptor(NetworkLoggerInterceptor())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://jsonplaceholder.typicode.com/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

### Example Usage

```kotlin

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
```

---

### License

```
MIT License

Copyright (c) 2025 Excelsior Technologies 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


