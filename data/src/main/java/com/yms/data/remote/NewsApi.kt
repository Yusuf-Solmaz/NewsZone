package com.yms.data.remote

import com.yms.data.BuildConfig
import com.yms.data.remote.dto.news.NewsRoot
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNewsByCategory(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("sources") source: String?,
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("category") category: String?,
        @Query("page") page: Int ,
        @Query("pageSize") pageSize: Int
    ): NewsRoot


    @GET("everything")
    suspend fun searchNews(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("q") query: String,
        @Query("searchIn") searchIn: String?,
        @Query("from") fromDate: String?,
        @Query("to") toDate: String?,
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String? = "publishedAt",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): NewsRoot

}