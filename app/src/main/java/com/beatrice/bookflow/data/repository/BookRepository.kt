package com.beatrice.bookflow.data.repository

import com.beatrice.bookflow.data.BookApiService
import com.beatrice.bookflow.data.mapper.toDomain
import com.beatrice.bookflow.domain.models.NetworkResult
import logcat.logcat


interface BookRepository {
    suspend fun fetchBooks(searchQuery: String, searchBy: String): NetworkResult
}


class BookRepositoryImpl(
    private val bookApiService: BookApiService
) : BookRepository {
    override suspend fun fetchBooks(searchQuery: String, searchby: String): NetworkResult {
        val params = mutableMapOf(
            "offset" to "0",
            "limit" to "20" // TODO  implement pagination
        )
        val key = if (searchby == "All") "q" else searchby.lowercase()
        params.put(key, searchQuery)
        return try {
            val searchResult = bookApiService.fetchBooks(params)
            val content = searchResult.toDomain()

            NetworkResult.Content(content)
        } catch (e: Exception) {
            logcat("SEARCH_REQUEST") { "Failed with ${e.localizedMessage}" }q
            NetworkResult.Error()
        }
    }
}