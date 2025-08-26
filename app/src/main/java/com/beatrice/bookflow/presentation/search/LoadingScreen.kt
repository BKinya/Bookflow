package com.beatrice.bookflow.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.squareup.workflow1.ui.compose.ComposeScreen

object LoadingScreen: ComposeScreen {

    @Composable
    override fun Content() {
        LoadingScreenContent(this)
    }
}

@Composable
private fun LoadingScreenContent(
    screen: LoadingScreen,
    modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    CircularProgressIndicator(
      color = MaterialTheme.colorScheme.primary,
      modifier = Modifier.padding(bottom = 16.dp)
    )


    Text(
      text = "Searching...",
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onBackground
    )
  }
}
