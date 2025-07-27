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
import com.beatrice.bookflow.presentation.RootNavigationWorkflow
import com.beatrice.bookflow.presentation.theme.BookFlowTheme
import com.squareup.workflow1.ui.compose.WorkflowRendering
import com.squareup.workflow1.ui.compose.renderAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val rendering by RootNavigationWorkflow.renderAsState(props = Unit, onOutput = {}) // compose state
            // TODO: Will this value be preserved when the activity is recreated?
            BookFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WorkflowRendering(rendering, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

