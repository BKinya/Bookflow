package com.beatrice.bookflow.presentation.searchResult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.beatrice.bookflow.domain.models.Book
import com.beatrice.bookflow.domain.models.SearchResult
import com.squareup.workflow1.ui.compose.ComposeScreen
import logcat.logcat
import java.util.UUID

data class SearchResultScreen(
    val query: String,
    val searchState: SearchResultWorkflow.SearchState
) : ComposeScreen {

    @Composable
    override fun Content() {
        logcat("SEARCH_REQUEST") { "search result screen.... $searchState" }
        SearchResultScreenContent(this)
    }
}

@Composable
private fun SearchResultScreenContent(
    screen: SearchResultScreen,
    modifier: Modifier = Modifier,
) {

    Scaffold { _ ->
        when (val state = screen.searchState) {
            is SearchResultWorkflow.SearchState.Content -> {
              Box() {
                Column() {
                  Text(
                    text = "Search results for \"${screen.query}\"",
                    style = TextStyle(
                      fontSize = 22.sp,
                      fontWeight = FontWeight.W500,
                      color = Color.DarkGray
                    )
                  )
                  Spacer(Modifier.height(12.dp))
                  Text(
                    text = "${state.result.numFound} results",
                    style = TextStyle(
                      fontSize = 14.sp,
                      color = Color.LightGray
                    )
                  )
                }
                LazyColumn {
                  items(
                    state.result.books,
                    key = { it.id }) { book ->
                    BookRow(book = book)
                  }
                }
              }
            }
            is SearchResultWorkflow.SearchState.Error -> ErrorScreen()
            SearchResultWorkflow.SearchState.Loading -> LoadingScreen()
        }

    }
}


@Composable
private fun BookRow(modifier: Modifier = Modifier, book: Book) {
    Row(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://covers.openlibrary.org/b/olid/${book.coverImageId}-M.jpg").build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(10.dp))
        Column {
            Text(
                text = book.title ?: "",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.DarkGray
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "by ${book.authors?.joinToString()}",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.DarkGray
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "first published  ${book.firstPublishYear}  -- ${book.editionCount} editions",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.DarkGray
                )
            )
        }
    }
}

@Composable
private fun ErrorScreen(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Red.copy(alpha = 0.8f))
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Oops! Something went wrong.",
      color = Color.White,
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(bottom = 8.dp)
    )

  }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
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

@Preview
@Composable
fun SearchResultPreview() {
    SearchResultScreenContent(
        screen = SearchResultScreen(
            query = "I know why the caged bird sings",
            searchState = SearchResultWorkflow.SearchState.Content(
                result = SearchResult(
                    numFound = 30,
                    books = listOf(
                        Book(
                            authors = listOf("Maya Angelou"),
                            editionCount = 40,
                            firstPublishYear = 2001,
                            title = "I know why the caged bird",
                            coverImageId = "OL24762814M",
                            id = UUID.randomUUID()
                        )
                    )
                )
            )

        )
    )
}
