package com.beatrice.bookflow.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.bookflow.presentation.search.SearchWorkflow
import com.squareup.workflow1.ui.Screen
import com.squareup.workflow1.ui.renderWorkflowIn
import kotlinx.coroutines.flow.Flow

class SearchViewModel( // TODO: Not sure I'll need this
    savedState: SavedStateHandle
):  ViewModel() {
    val renderings: Flow<Screen> by lazy {
        renderWorkflowIn(
            workflow = SearchWorkflow,
            scope = viewModelScope,
            savedStateHandle = savedState
        )
    }
}