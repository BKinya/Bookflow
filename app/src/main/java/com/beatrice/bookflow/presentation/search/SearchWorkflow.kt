package com.beatrice.bookflow.presentation.search

import com.beatrice.bookflow.presentation.search.SearchWorkflow.SearchRequest
import com.beatrice.bookflow.presentation.search.SearchWorkflow.State
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.ui.TextController


object SearchWorkflow : StatefulWorkflow<Unit, State, SearchRequest, SearchScreen>() {

    data class SearchRequest(
        val searchBy: String,
        val query: String
    )

    data class State(
        val searchByOptions: List<String>,
        val searchBy: TextController,
        val query: TextController,
        val errorMsg: String? = null
    )

    override fun initialState(
        props: Unit,
        snapshot: Snapshot?
    ): State = State(
        searchByOptions = OPTIONS,
        searchBy = TextController(initialValue = OPTIONS[0]),
        query = TextController()
    )

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): SearchScreen {
        return SearchScreen(
            searchByOptions = renderState.searchByOptions,
            searchBy = renderState.searchBy,
            query = renderState.query,
            errorMsg = renderState.errorMsg,
            onSearchTapped = context.eventHandler("onSearchTapped") {
                if (renderState.query.textValue.isEmpty()) {
                    state = state.copy(errorMsg = "Search Query is required")
                } else {
                    setOutput(
                        SearchRequest(
                            searchBy = renderState.searchBy.textValue,
                            query = renderState.query.textValue
                        )
                    )
                }
            }
        )
    }

    override fun snapshotState(state: State): Snapshot? = null

}
