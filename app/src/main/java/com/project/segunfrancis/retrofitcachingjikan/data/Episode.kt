package com.project.segunfrancis.retrofitcachingjikan.data

import com.google.gson.annotations.SerializedName

/**
 * Created by SegunFrancis
 */

data class Episode(
    @SerializedName("episode_id")
    val episodeId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_japanese")
    val titleJapanese: String,
    @SerializedName("title_romanji")
    val titleRomanji: String,
    @SerializedName("aired")
    val aired: String,
    @SerializedName("filler")
    val filler: String,
    @SerializedName("recap")
    val recap: String,
    @SerializedName("video_url")
    val videoUrl: String,
    @SerializedName("forum_url")
    val forumUrl: String
)