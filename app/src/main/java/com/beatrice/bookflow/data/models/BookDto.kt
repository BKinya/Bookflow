package com.beatrice.bookflow.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val author_key: List<String>? = null,
    val author_name: List<String>?= null,
    val cover_edition_key: String? = null,
    val cover_i: Int?= null ,
    val ebook_access: String? = null,
    val edition_count: Int? = null,
    val first_publish_year: Int? = null,
    val has_fulltext: Boolean? = null,
    val ia: List<String>?= null,
    val ia_collection_s: String?= null,
    val key: String? = null,
    val language: List<String>?= null,
    val lending_edition_s: String?= null,
    val lending_identifier_s: String? =null,
    val public_scan_b: Boolean? =null,
    val title: String? = null
)