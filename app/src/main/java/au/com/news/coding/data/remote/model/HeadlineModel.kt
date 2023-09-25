package au.com.news.coding.data.remote.model

import com.google.gson.annotations.SerializedName


data class HeadlineModel(

    @SerializedName("status") val status: String?,
    @SerializedName("totalResults") val totalResults: Int?,
    @SerializedName("articles") val articles: List<Article>

)