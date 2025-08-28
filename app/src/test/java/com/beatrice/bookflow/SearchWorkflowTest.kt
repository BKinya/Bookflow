package com.beatrice.bookflow

import com.beatrice.bookflow.presentation.search.OPTIONS
import com.beatrice.bookflow.presentation.search.SearchWorkflow
import com.squareup.workflow1.testing.testRender
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SearchWorkflowTest {

    @Test
    fun `user submits search query successfully`() {
        SearchWorkflow.testRender(props = Unit).render { screen ->
            assertEquals(screen.searchBy.textValue, OPTIONS[0])
            screen.searchBy.textValue = "Title"
            screen.query.textValue = "The hitchhiker's guide to the galaxy"
            screen.onSearchTapped()
        }
            .verifyActionResult { _, output ->
                assertEquals(
                    SearchWorkflow.SearchRequest(
                        searchBy = "Title",
                        query = "The hitchhiker's guide to the galaxy"
                    ),
                    output?.value
                )
            }
    }

    @Test
    fun `user submits empty search query failed`() {
        SearchWorkflow.testRender(props = Unit).render { screen ->
            assertEquals(screen.searchBy.textValue, OPTIONS[0])
            screen.onSearchTapped()
        }
            .verifyActionResult { _, output ->
                // No output will be emitted when search query is empty
                assertNull(output)
            }
            .testNextRender()
            .render{ screen ->
                assertEquals("Search Query is required", screen.message)
            }
    }
}