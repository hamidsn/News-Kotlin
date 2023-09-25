package au.com.news.coding.data.remote

import au.com.news.coding.BuildConfig
import au.com.news.coding.data.remote.model.HeadlineModel
import au.com.news.coding.data.remote.model.SourceDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines/sources")
    suspend fun sources(
        @Query("apiKey") key: String = BuildConfig.API_KEY
    ): SourceDto

    @GET("/v2/top-headlines")
    suspend fun headLines(
        @Query("sources") sources: String = "",
        @Query("apiKey") key: String = BuildConfig.API_KEY
    ): HeadlineModel

}