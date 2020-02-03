package com.project.segunfrancis.retrofitcachingjikan.api

import com.project.segunfrancis.retrofitcachingjikan.data.BaseResponse
import com.project.segunfrancis.retrofitcachingjikan.data.Episode
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by SegunFrancis
 */
interface Service {

    @GET("anime/1/episodes")
    fun getEpisodes(): Call<BaseResponse>
}