package hrhera.ali.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import hrhera.ali.network.BuildConfig
import hrhera.ali.network.interceptors.AuthKeyInterceptor

const val BASE_URL =BuildConfig.BASE_URL

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val READ_TIMEOUT = 30
    private const val WRITE_TIMEOUT = 30
    private const val CONNECTION_TIMEOUT = 10
    private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB


    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    internal fun provideCache(@ApplicationContext context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }
    @Provides
    @Singleton
    internal fun provideAuthKeyInterceptor(): AuthKeyInterceptor {
        return AuthKeyInterceptor()
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authKeyInterceptor: AuthKeyInterceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache)
            connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            addNetworkInterceptor(authKeyInterceptor)
            if(BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(client: OkHttpClient, ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}