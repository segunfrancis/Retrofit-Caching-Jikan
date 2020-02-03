package com.project.segunfrancis.retrofitcachingjikan.data

import com.google.gson.annotations.SerializedName

/**
 * Created by SegunFrancis
 */

data class BaseResponse(@SerializedName("episodes") val episodes: List<Episode>)