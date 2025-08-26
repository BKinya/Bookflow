package com.beatrice.bookflow.presentation.searchResult

import com.beatrice.bookflow.domain.models.SearchResult
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.BackPressed
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchResultProps
import com.squareup.workflow1.StatelessWorkflow


object SearchResultWorkflow :
    StatelessWorkflow<SearchResultProps, BackPressed, SearchResultScreen>() {

    data class SearchResultProps(
        val query: String,
        val searchBy: String,
        val result: SearchResult
    )

    data object BackPressed

    override fun render(
        renderProps: SearchResultProps,
        context: RenderContext
    ): SearchResultScreen {

        val resultScreen = SearchResultScreen(
            query = renderProps.query,
            result = renderProps.result,
            onBackPressed = context.eventHandler("onBackPressed") {
                setOutput(BackPressed)
            }
        )
        return resultScreen
    }
}
