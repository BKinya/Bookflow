package com.beatrice.bookflow.domain.models

import java.util.UUID

data class Book(
    val id: UUID,
    val authors: List<String>?,
    val editionCount: Int?,
    val firstPublishYear: Int?,
    val title: String?,
    val coverImageId: String?,
    // TODO: Add ratings
)
