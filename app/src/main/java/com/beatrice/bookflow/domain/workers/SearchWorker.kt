package com.beatrice.bookflow.domain.workers

import com.beatrice.bookflow.data.repository.BookRepository
import com.beatrice.bookflow.domain.models.NetworkResult
import com.squareup.workflow1.Worker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchWorker(
    private val repository: BookRepository,
    private val searchQuery: String,
    private val searchBy: String
) : Worker<NetworkResult> {
    override fun run(): Flow<NetworkResult> = flow {
        val result = repository.fetchBooks(
            searchQuery = searchQuery,
            searchBy = searchBy
        )
        emit(result)
    }

    override fun doesSameWorkAs(otherWorker: Worker<*>): Boolean {
        return otherWorker is SearchWorker &&
                otherWorker.searchQuery == this.searchQuery &&
                otherWorker.searchBy == this.searchBy
    }

}