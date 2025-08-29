package com.beatrice.bookflow.presentation

import com.beatrice.bookflow.data.repository.BookRepository
import com.beatrice.bookflow.domain.models.NetworkResult
import com.beatrice.bookflow.domain.models.SearchResult
import com.beatrice.bookflow.domain.workers.SearchWorker
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State.LoadingSearchResult
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State.ShowSearchResult
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State.ShowSearchScreen
import com.beatrice.bookflow.presentation.search.LoadingScreen
import com.beatrice.bookflow.presentation.search.SearchWorkflow
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchResultProps
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.runningWorker
import com.squareup.workflow1.ui.TextController
import com.squareup.workflow1.ui.navigation.BackStackScreen
import com.squareup.workflow1.ui.navigation.toBackStackScreen
import org.koin.java.KoinJavaComponent.inject

object RootNavigationWorkflow : StatefulWorkflow<Unit, State, Nothing, BackStackScreen<*>>() {
    val repository: BookRepository by inject(BookRepository::class.java)


    sealed interface State {
        data object ShowSearchScreen : State
        data class LoadingSearchResult(
            val searchBy: String,
            val query: String
        ) : State

        data class ShowSearchResult(
            val searchBy: String,
            val searchQuery: String,
            val result: SearchResult
        ) : State

        data class ShowError(
            val message: String,
            val searchBy: String,
            val query: String
        ) : State

    }

    override fun initialState(props: Unit, snapshot: Snapshot?): State =
        ShowSearchScreen

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): BackStackScreen<*> {
        val searchScreen = context.renderChild(
            child = SearchWorkflow,
            props = null,
            key = "search"
        ) { output ->
            onSearch(output.searchBy, output.query)
        }
        return when (renderState) {
            is ShowSearchScreen -> {
                BackStackScreen(searchScreen)
            }

            is LoadingSearchResult -> {
                context.runningWorker(
                    SearchWorker(
                        repository = repository,
                        searchBy = renderState.searchBy,
                        searchQuery = renderState.query
                    ),
                    key = "Search Worker"
                ) { result ->
                    when (result) {
                        is NetworkResult.Content -> updateState(
                            newState = ShowSearchResult(
                                searchBy = renderState.searchBy,
                                searchQuery = renderState.query,
                                result = result.searchResult
                            )
                        )

                        is NetworkResult.Error -> updateState(
                            newState =
                                State.ShowError(
                                    message = "Unfortunately we have an issue with your request. " +
                                            "Try again later",
                                    searchBy = renderState.searchBy,
                                    query = renderState.query
                                )
                        )
                    }
                }
                BackStackScreen(LoadingScreen)
            }

            is ShowSearchResult -> {
                val resultScreen = context.renderChild(
                    child = SearchResultWorkflow,
                    props = SearchResultProps(
                        query = renderState.searchQuery,
                        searchBy = renderState.searchBy,
                        result = renderState.result
                    )
                ) { output ->
                    onBackNavigation()
                }
                listOf(searchScreen, resultScreen).toBackStackScreen()

            }

            is State.ShowError -> {
                val searchScreenWithError = context.renderChild(
                    child = SearchWorkflow,
                    props = SearchWorkflow.SearchProps(
                        message = renderState.message,
                        searchBy = TextController(renderState.searchBy),
                        query = TextController(renderState.query)
                    ),
                    key = "searchWithError"
                ) { output ->
                    println("mhhhh")
                    onSearch(output.searchBy, output.query)
                }
                BackStackScreen(searchScreenWithError)
            }

        }
    }

    override fun snapshotState(state: State): Snapshot? = null
    private fun updateState(newState: State) = action("onSearchResult") {
        state = newState
    }


    private fun onSearch(searchBy: String, query: String) = action("onSearch") {
        state = LoadingSearchResult(searchBy = searchBy, query = query)
    }

    private fun onBackNavigation() = action("backNavigation") {
        state = ShowSearchScreen
    }
}
