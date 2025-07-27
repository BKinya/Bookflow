package com.beatrice.bookflow.presentation

import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State.ShowSearchResult
import com.beatrice.bookflow.presentation.search.SearchWorkflow
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchResultProps
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.ui.Screen
import logcat.logcat

object RootNavigationWorkflow : StatefulWorkflow<Unit, State, Nothing, Screen>() {

    sealed interface State {
        data object ShowSearchScreen : State
        data class ShowSearchResult(
            val searchBy: String,
            val searchQuery: String,
            val searchState: SearchState.Loading
        ) : State


    }

    override fun initialState(props: Unit, snapshot: Snapshot?): State =
        State.ShowSearchScreen

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): Screen {
        // TODO: Add Back navigation animation
        when (renderState) {
            is ShowSearchResult -> {
                logcat("SEARCH_REQUEST") { "here several times" }
                val resultScreen = context.renderChild(
                    child = SearchResultWorkflow,
                    props = SearchResultProps(
                        query = renderState.searchQuery,
                        searchBy = renderState.searchBy,
                        searchState = renderState.searchState
                    )
                ) { output ->
                    TODO()
                }

                return resultScreen
            }

            is State.ShowSearchScreen -> {
                val searchScreen = context.renderChild(
                    child = SearchWorkflow,
                    props = Unit
                ) { output ->
                    onSearch(output.searchBy, output.query)
                }
                return searchScreen
            }
        }

    }


    override fun snapshotState(state: State): Snapshot? = null

    private fun onSearch(searchBy: String, query: String) = action("search") {
        state = ShowSearchResult(
            searchBy = searchBy,
            searchQuery = query,
            searchState = SearchState.Loading
        )
    }

}
