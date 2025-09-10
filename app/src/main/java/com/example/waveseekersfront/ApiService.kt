package com.example.waveseekersfront

import android.util.Log
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface ApiService {
    companion object {
        private const val BASE_URL = "https://localhost:8443/"
        private val gson = Gson()

        fun fetchHelloMessage(): String? {
            val client = getUnsafeOkHttpClient()
            val request = okhttp3.Request.Builder()
                .url("${BASE_URL}hello")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                return response.body?.string()
            }
        }

        fun fetchCountries(): List<ApiCountry>? {
            val client = getUnsafeOkHttpClient()
            val request = Request.Builder()
                .url("${BASE_URL}countries")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    Log.d("API_COUNTRIES", "Response code: ${response.code}")
                    if (!response.isSuccessful) {
                        Log.e("API_COUNTRIES", "Unsuccessful response: $response")
                        throw IOException("Unexpected code $response")
                    }

                    val responseBody = response.body?.string()
                    Log.d("API_COUNTRIES", "Response body: $responseBody")

                    if (responseBody != null) {
                        val listType = object : TypeToken<List<ApiCountry>>() {}.type
                        val countries = gson.fromJson<List<ApiCountry>>(responseBody, listType)
                        Log.d("API_COUNTRIES", "Parsed ${countries?.size ?: 0} countries")
                        return countries
                    }
                    return null
                }
            } catch (e: Exception) {
                Log.e("API_COUNTRIES", "Exception fetching countries", e)
                e.printStackTrace()
                return null
            }
        }

        fun fetchSpotList(): List<ApiSpot>? {
            val client = getUnsafeOkHttpClient()
            val request = Request.Builder()
                .url("${BASE_URL}spots")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected code $response")
                    }

                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val listType = object : TypeToken<List<ApiSpot>>() {}.type
                        return gson.fromJson(responseBody, listType)
                    }
                    return null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            return OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .build()
        }
    }
}
