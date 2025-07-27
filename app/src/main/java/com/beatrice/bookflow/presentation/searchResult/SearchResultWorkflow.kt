package com.beatrice.bookflow.presentation.searchResult

import com.beatrice.bookflow.data.repository.BookRepository
import com.beatrice.bookflow.domain.models.NetworkResult
import com.beatrice.bookflow.domain.models.SearchResult
import com.beatrice.bookflow.domain.workers.SearchWorker
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchOutput
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchResultProps
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.runningWorker
import logcat.logcat
import org.koin.java.KoinJavaComponent.inject


object SearchResultWorkflow :
    StatefulWorkflow<SearchResultProps, SearchState, SearchOutput, SearchResultScreen>() {

    val repository: BookRepository by inject(BookRepository::class.java)

    data class SearchResultProps(
        val query: String,
        val searchBy: String,
        val searchState: SearchState.Loading
    )

    sealed interface SearchOutput {// Maybe I'll not need this TODO
        data class BookResult(val data: SearchResult) : SearchOutput
        @JvmInline
        value class SearchFailed(val errorMessage: String) : SearchOutput
    }

    sealed interface SearchState {
        data object Loading : SearchState
        data class Content(val result: SearchResult) : SearchState

        @JvmInline
        value class Error(val message: String) : SearchState
    }

    override fun initialState(
        props: SearchResultProps,
        snapshot: Snapshot?
    ): SearchState = SearchState.Loading

    override fun render(
        renderProps: SearchResultProps,
        renderSearchState: SearchState,
        context: RenderContext
    ): SearchResultScreen {
        if (renderSearchState is SearchState.Loading) {
            context.runningWorker(// On what thread though, is it blocking no
                SearchWorker(
                    repository = repository,
                    searchBy = renderProps.searchBy,
                    searchQuery = renderProps.query
                ),
                key = "Search Worker"
            ) { result ->
                logcat("SEARCH_REQUEST") { "ui result $result" }
                when (result) {
                    is NetworkResult.Content -> updateState(newState = SearchState.Content(result.searchResult))
                    is NetworkResult.Error -> updateState(newState = SearchState.Error("Something"))
                }
            }
        }
        val resultScreen = SearchResultScreen(
            query = renderProps.searchBy,
            searchState = renderSearchState
        )
        return resultScreen
    }

    override fun snapshotState(searchState: SearchState): Snapshot? = null


    private fun updateState(newState: SearchState ) = action("onSearchResult") {
        logcat("SEARCH_REQUEST") { "update state action" }
        state = newState
    }

}
