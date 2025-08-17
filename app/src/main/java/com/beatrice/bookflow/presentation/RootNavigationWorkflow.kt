package com.beatrice.bookflow.presentation

import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State.ShowSearchResult
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State.ShowSearchScreen
import com.beatrice.bookflow.presentation.search.SearchWorkflow
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchResultProps
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.ui.navigation.BackStackScreen
import com.squareup.workflow1.ui.navigation.toBackStackScreen

object RootNavigationWorkflow : StatefulWorkflow<Unit, State, Nothing, BackStackScreen<*>>() {

    sealed interface State {
        data object ShowSearchScreen : State
        data class ShowSearchResult(
            val searchBy: String,
            val searchQuery: String,
            val searchState: SearchState.Loading
        ) : State
    }

    override fun initialState(props: Unit, snapshot: Snapshot?): State = ShowSearchScreen

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): BackStackScreen<*> {
        val searchScreen = context.renderChild(
            child = SearchWorkflow,
            props = Unit
        ) { output ->
            onSearch(output.searchBy, output.query)
        }
       return when (renderState) {
            is ShowSearchResult -> {
                val resultScreen = context.renderChild(
                    child = SearchResultWorkflow,
                    props = SearchResultProps(
                        query = renderState.searchQuery,
                        searchBy = renderState.searchBy,
                        searchState = renderState.searchState
                    )
                ) { output ->
                    onBackNavigation()
                }
                listOf(searchScreen, resultScreen).toBackStackScreen()

            }

            is ShowSearchScreen -> {
                BackStackScreen(searchScreen)
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

    private fun onBackNavigation() = action("backNavigation") {
        state = ShowSearchScreen
    }
}
