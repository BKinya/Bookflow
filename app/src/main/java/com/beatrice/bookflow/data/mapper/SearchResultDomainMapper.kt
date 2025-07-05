package com.beatrice.bookflow.data.mapper

import com.beatrice.bookflow.data.models.BookDto
import com.beatrice.bookflow.data.models.SearchResultDto
import com.beatrice.bookflow.domain.models.Book
import com.beatrice.bookflow.domain.models.SearchResult

fun SearchResultDto.toDomain() = SearchResult(
    books =  books.map { it.toDomain() },
    numFound = numFound,
    numFoundExact = numFoundExact
)


fun BookDto.toDomain() = Book(
    authorName = author_name ,
    ebookAccess = ebook_access,
    firstPublishYear = first_publish_year ,
    editionCount = edition_count ,
    hasFulltext = has_fulltext,
    language = language ,
    title = title ,
    publicScanB = public_scan_b
)

