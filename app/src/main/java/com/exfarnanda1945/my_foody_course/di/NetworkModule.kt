package com.exfarnanda1945.my_foody_course.di

import com.exfarnanda1945.my_foody_course.data.network.FoodRecipesApi
import com.exfarnanda1945.my_foody_course.util.Constants.API_KEY
import com.exfarnanda1945.my_foody_course.util.Constants.BASE_URL
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val url = request.url().newBuilder().addQueryParameter(
            QUERY_API_KEY, API_KEY
        ).build()

        chain.proceed(
            request.newBuilder().url(url).build()
        )
    }

    @Singleton
    @Provides
    fun provideConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(provideInterceptor())
        .build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        converter: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converter)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodRecipesApi =
        retrofit.create(FoodRecipesApi::class.java)
}