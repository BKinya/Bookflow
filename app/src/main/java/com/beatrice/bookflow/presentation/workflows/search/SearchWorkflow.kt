package com.beatrice.bookflow.presentation.workflows.search

import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.ui.TextController
import com.beatrice.bookflow.presentation.workflows.search.SearchWorkflow.State

private val OPTIONS = listOf("All", "Title", "Author", "Text", "Subject", "Lists")
object SearchWorkflow : StatefulWorkflow<Unit, State, Unit, SearchScreen>() {

    data class State(
        val searchByOptions: List<String>  ,
        val searchBy: TextController,
        val query: TextController,
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
    ): SearchScreen  = SearchScreen(
        searchByOptions = renderState.searchByOptions,
        searchBy = renderState.searchBy,
        query = renderState.query,
        onSearch = {
            println("submit clicked")
        }
    )

    override fun snapshotState(state: State): Snapshot? = null
}
