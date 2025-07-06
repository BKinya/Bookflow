package com.beatrice.bookflow.domain.models

data class Book(
    val authorName: List<String>?,
    val ebookAccess: String?,
    val editionCount: Int?,
    val firstPublishYear: Int?,
    val hasFulltext: Boolean?,
    val language: List<String>?,
    val publicScanB: Boolean?,
    val title: String?
)
