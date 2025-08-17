package com.beatrice.bookflow.domain.models

sealed interface NetworkResult {
    data class Content(val searchResult: SearchResult): NetworkResult
    @JvmInline value class Error(val message: String = "Oops! Something went wrong try again later"): NetworkResult
}