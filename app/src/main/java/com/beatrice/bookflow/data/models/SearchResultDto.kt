package com.beatrice.bookflow.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    @SerialName("docs")
    val books: List<BookDto>,
    val numFound: Int,
    val numFoundExact: Boolean,
)