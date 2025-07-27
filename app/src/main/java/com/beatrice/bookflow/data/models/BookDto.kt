package com.beatrice.bookflow.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val author_name: List<String>?= null, //
    val cover_edition_key: String? = null,
    val edition_count: Int? = null,
    val first_publish_year: Int? = null,
    val key: String? = null, //  TODO: get ratings: https://openlibrary.org/works/OL27448W/ratings.json
    val title: String? = null //
)