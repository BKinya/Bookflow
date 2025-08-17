package com.beatrice.bookflow

import com.beatrice.bookflow.presentation.RootNavigationWorkflow
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State.ShowSearchResult
import com.beatrice.bookflow.presentation.RootNavigationWorkflow.State.ShowSearchScreen
import com.beatrice.bookflow.presentation.search.OPTIONS
import com.beatrice.bookflow.presentation.search.SearchScreen
import com.beatrice.bookflow.presentation.search.SearchWorkflow
import com.beatrice.bookflow.presentation.searchResult.SearchResultWorkflow.SearchState
import com.squareup.workflow1.WorkflowOutput
import com.squareup.workflow1.testing.expectWorkflow
import com.squareup.workflow1.testing.testRender
import kotlin.test.Test
import kotlin.test.assertEquals

class RootNavigationWorkflowTest {

    @Test
    fun `search rendering`() {
        RootNavigationWorkflow.testRender(
            props = Unit,
            initialState = ShowSearchScreen,
        )
            .expectWorkflow(
                workflowType = SearchWorkflow::class,
                rendering = SearchScreen(),
                // simulate workflow sending an output ShowSearchResult when `search` button is tapped
                output = WorkflowOutput(
                    SearchWorkflow.SearchRequest(
                        searchBy = "Title",
                        query = "The hitchhiker's guide to the galaxy"
                    )
                )
            )
            // validate that there is only 1 item in the BackStackScreen
            .render { rendering ->
                val frames = rendering.frames
                assertEquals(1, frames.size)

                val searchScreen = frames[0] as SearchScreen
                assertEquals(OPTIONS[0], searchScreen.searchBy.textValue)
            }
            // Verify state transitioned to ShowSearchResult
            .verifyActionResult { newState, _ ->
                assertEquals(
                    ShowSearchResult(
                        searchBy = "Title",
                        searchQuery = "The hitchhiker's guide to the galaxy",
                        searchState = SearchState.Loading
                    ),
                    newState
                )
            }
    }
}