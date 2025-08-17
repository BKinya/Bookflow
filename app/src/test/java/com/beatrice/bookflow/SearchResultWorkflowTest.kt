package com.beatrice.bookflow

import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchState
import com.squareup.workflow1.testing.testRender
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SearchResultWorkflowTest : TestApplication() {

    @Test
    fun `search state is loading`() {

        SearchResultWorkflow.testRender(
            props = SearchResultWorkflow.SearchResultProps(
                searchBy = "Title",
                query = "The hitchhiker's guide to the galaxy",
                searchState = SearchState.Loading
            )
        )
            .render { screen ->
                assertTrue(
                    screen.searchState is SearchState.Loading
                )
            }

    }
}