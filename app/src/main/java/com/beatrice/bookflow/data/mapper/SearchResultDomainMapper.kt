package com.beatrice.bookflow.data.mapper

import com.beatrice.bookflow.data.models.BookDto
import com.beatrice.bookflow.data.models.SearchResultDto
import com.beatrice.bookflow.domain.models.Book
import com.beatrice.bookflow.domain.models.SearchResult
import java.util.UUID

fun SearchResultDto.toDomain() = SearchResult(
    books =  books.map { it.toDomain() },
    numFound = numFound,
)


fun BookDto.toDomain() = Book(
    authors = author_name,
    firstPublishYear = first_publish_year ,
    editionCount = edition_count,
    title = title,
    coverImageId = cover_edition_key,
    id = UUID.randomUUID()
)

