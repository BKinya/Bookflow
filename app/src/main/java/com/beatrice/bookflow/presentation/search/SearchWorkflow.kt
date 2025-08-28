package com.beatrice.bookflow.presentation.search

import com.beatrice.bookflow.presentation.search.SearchWorkflow.SearchRequest
import com.beatrice.bookflow.presentation.search.SearchWorkflow.State
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.ui.TextController


object SearchWorkflow : StatefulWorkflow<String, State, SearchRequest, SearchScreen>() {

    data class SearchRequest(
        val searchBy: String,
        val query: String
    )

    data class State(
        val searchByOptions: List<String>,
        val searchBy: TextController,
        val query: TextController,
        val message: String = ""
    )

    override fun initialState(
        props: String,
        snapshot: Snapshot?
    ): State = State(
        searchByOptions = OPTIONS,
        searchBy = TextController(initialValue = OPTIONS[0]),
        query = TextController(),
        message = props
    )

    override fun render(
        renderProps: String,
        renderState: State,
        context: RenderContext
    ): SearchScreen {
        return SearchScreen(
            searchByOptions = renderState.searchByOptions,
            searchBy = renderState.searchBy,
            query = renderState.query,
            message = renderState.message,
            onSearchTapped = context.eventHandler("onSearchTapped") {
                if (renderState.query.textValue.isEmpty()) {
                    state = state.copy(message = "Enter a keyword to start searching")
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
