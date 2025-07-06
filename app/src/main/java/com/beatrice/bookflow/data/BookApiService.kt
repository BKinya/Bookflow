package com.beatrice.bookflow.data

import com.beatrice.bookflow.data.models.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BookApiService {
    @GET("search.json")
    suspend fun fetchBooks(
        @QueryMap params: Map<String, String>
    ): SearchResultDto
}