package com.beatrice.bookflow.domain.models

import com.beatrice.bookflow.data.models.BookDto

sealed interface NetworkResult {
    data class Content(val searchResult: SearchResult): NetworkResult
    @JvmInline value class Error(val message: String = "Oops! Something went wrong try again later"): NetworkResult
}