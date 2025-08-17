package com.beatrice.bookflow.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.bookflow.presentation.RootNavigationWorkflow
import com.squareup.workflow1.WorkflowExperimentalRuntime
import com.squareup.workflow1.mapRendering
import com.squareup.workflow1.ui.Screen
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.compose.withComposeInteropSupport
import com.squareup.workflow1.ui.renderWorkflowIn
import com.squareup.workflow1.ui.withEnvironment
import kotlinx.coroutines.flow.Flow

private val viewEnvironment = ViewEnvironment.EMPTY.withComposeInteropSupport()

class SearchViewModel(
    savedState: SavedStateHandle
):  ViewModel() {
    @OptIn(WorkflowExperimentalRuntime::class)
    val renderings: Flow<Screen> by lazy {
        renderWorkflowIn(
            workflow = RootNavigationWorkflow
                .mapRendering { it.withEnvironment(viewEnvironment) },
            scope = viewModelScope,
            savedStateHandle = savedState,
        )
    }
}