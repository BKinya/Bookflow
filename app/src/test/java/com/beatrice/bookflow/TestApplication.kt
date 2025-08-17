package com.beatrice.bookflow

import com.beatrice.bookflow.data.repository.BookRepository
import com.beatrice.bookflow.domain.models.NetworkResult
import com.beatrice.bookflow.domain.models.SearchResult
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

open class TestApplication: KoinTest {
    @BeforeTest
    fun setUp() {
        startKoin {
            modules(testModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}

val testModule = module {
    single<BookRepository>{ FakeRepository() }
}

class FakeRepository: BookRepository {
    override suspend fun fetchBooks(
        searchQuery: String,
        searchBy: String
    ): NetworkResult {
        print("fetching books")
        return NetworkResult.Content(searchResult = SearchResult(
            books = emptyList(),
            numFound = 40
        ))
    }
}