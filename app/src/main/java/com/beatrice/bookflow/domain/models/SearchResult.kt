package com.beatrice.bookflow.domain.models

data class SearchResult(
    val books: List<Book>,
    val numFound: Int,
    val numFoundExact: Boolean,
)
