package hrhera.ali.network.interceptors

import hrhera.ali.network.KeysProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthKeyInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("appid", KeysProvider.getApiKey())
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}

