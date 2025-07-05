package com.beatrice.bookflow.data.repository

import com.beatrice.bookflow.data.BookApiService
import com.beatrice.bookflow.data.mapper.toDomain
import com.beatrice.bookflow.domain.models.NetworkResult
import logcat.logcat


interface BookRepository {
    suspend fun fetchBooks(searchQuery: String? = null, searchParams: Map<String, String>? = null): NetworkResult
}


class BookRepositoryImpl(
    private val bookApiService: BookApiService
): BookRepository{
    override suspend fun fetchBooks(searchQuery: String?, searchParams: Map<String, String>?): NetworkResult {
        val params = mutableMapOf(
            "offset" to "0",
            "limit" to "20" // Should update when I implement pagination
        )
        searchQuery?.let {
            params.put("q", searchQuery)
        }
        searchParams?.let {
            params += searchParams
        }
        logcat("SEARCH_PARAMS"){" are $params"}
    return    try {
            val searchResult = bookApiService.fetchBooks(params)
            val content = searchResult.toDomain()
            NetworkResult.Content(content)
        }catch (e: Exception){ // Should I be worried about coroutines exception here
            logcat("SEARCH_REQUEST"){"Failed with ${e.localizedMessage}"}
            NetworkResult.Error()
        }
    }
}