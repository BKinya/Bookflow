package com.beatrice.bookflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.beatrice.bookflow.presentation.search.SearchScreen
import com.beatrice.bookflow.presentation.theme.BookFlowTheme
import com.beatrice.bookflow.presentation.viewmodel.SearchViewModel
import com.squareup.workflow1.ui.compose.WorkflowRendering

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchViewModel: SearchViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            val rendering by searchViewModel.renderings.collectAsState(
                initial = SearchScreen()
            )

            BookFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WorkflowRendering(rendering, Modifier.padding(innerPadding))
                }
            }
        }
    }
}





