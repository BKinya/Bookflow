package com.beatrice.bookflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.beatrice.bookflow.presentation.theme.BookFlowTheme
import com.beatrice.bookflow.presentation.workflows.search.SearchWorkflow
import com.squareup.workflow1.ui.compose.WorkflowRendering
import com.squareup.workflow1.ui.compose.renderAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val rendering by SearchWorkflow.renderAsState(props = Unit, onOutput = {})
            BookFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WorkflowRendering(rendering, Modifier.padding(innerPadding))

                }
            }
        }
    }
}

