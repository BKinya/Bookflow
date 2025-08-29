package com.beatrice.bookflow

import com.beatrice.bookflow.presentation.search.OPTIONS
import com.beatrice.bookflow.presentation.search.SearchScreen
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.StatefulWorkflow.RenderContext
import com.squareup.workflow1.ui.TextController

object SearchWorkflow : StatefulWorkflow<String, State, SearchRequest, SearchScreen>() {

    ...

    override fun render(
        renderProps: String,
        renderState: State,
        context: RenderContext
    ): SearchScreen {
        println("New State search --> $renderState")
        return SearchScreen(
            searchByOptions = renderState.searchByOptions,
            searchBy = renderState.searchBy,
            query = renderState.query,
            message = renderProps,
            onSearchTapped = {}
        )
    }
    ...
}