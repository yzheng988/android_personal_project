package com.myProj.sample.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogRepository {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(addOkhttpClient())
        .build()
        .create(DogsApi::class.java)

    suspend fun getDogsCoroutine(): List<DogBreed> = api.getDogsCoroutine()

    private fun addOkhttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }
    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com"
    }
}