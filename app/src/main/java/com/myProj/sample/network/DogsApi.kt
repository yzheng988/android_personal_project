package com.myProj.sample.network

import com.myProj.sample.model.DogBreed
import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>

    @GET("DevTides/DogsApi/master/dogs.json")
    suspend fun getDogsCoroutine(): List<DogBreed>
}