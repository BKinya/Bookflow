package com.beatrice.bookflow.presentation.searchResult

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.beatrice.bookflow.R
import com.beatrice.bookflow.domain.models.Book
import com.beatrice.bookflow.domain.models.SearchResult
import com.squareup.workflow1.ui.compose.ComposeScreen
import java.util.UUID

data class SearchResultScreen(
    val query: String,
    val result: SearchResult,
    val onBackPressed: () -> Unit = {}
) : ComposeScreen {

    @Composable
    override fun Content() {
        BackHandler(
            onBack = onBackPressed
        )
        SearchResultScreen(
            screen = this,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun SearchResultScreen(
    screen: SearchResultScreen,
    modifier: Modifier = Modifier,
) {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Gray.toArgb())
    }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        Scaffold(
            modifier = modifier,
            topBar = {
                HeaderColumn(
                    onBackPressed = screen.onBackPressed,
                    query = screen.query,
                    resultCount = screen.result.numFound
                )

            }) { contentPadding ->
            ContentScreen(
                modifier = Modifier.padding(contentPadding),
                content = screen,
            )
        }
    }
}

@Composable
private fun HeaderColumn(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    query: String,
    resultCount: Int
) {
    Column(modifier = modifier.fillMaxWidth()) {
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = "Back navigation arrow"
            )
        }
        Spacer(Modifier.height(7.dp))
        Header(
            modifier = Modifier,
            query = query,
            resultCount = resultCount
        )
    }
}

@Composable
private fun ContentScreen(
    modifier: Modifier = Modifier,
    content: SearchResultScreen,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(
            items = content.result.books,
            key = { index, book -> book.id }
        ) { index, book ->
            BookRow(
                book = book,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            )

            if (index != content.result.books.lastIndex) {
                HorizontalDivider(
                    thickness = 1.3.dp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    query: String,
    resultCount: Int
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "Search results for \"$query\"",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.height(2.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "$resultCount results",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun BookCover(
    modifier: Modifier = Modifier,
    coverImageId: String?
) {
    if (coverImageId == null) {
        Image(
            painterResource(R.drawable.book_placeholder),
            contentDescription = "Placeholder book cover",
            modifier = modifier.fillMaxHeight(),
            contentScale = ContentScale.Fit
        )
    } else {
        AsyncImage(
            model = "https://covers.openlibrary.org/b/olid/${coverImageId}-M.jpg",
            contentDescription = null,
            placeholder = painterResource(R.drawable.book_placeholder),
            error = painterResource(R.drawable.book_placeholder),
            modifier = modifier.fillMaxHeight(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun BookRow(modifier: Modifier = Modifier, book: Book) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(modifier = Modifier) {
            BookCover(
                coverImageId = book.coverImageId,
                modifier = Modifier
                    .fillMaxHeight(5f / 6f)
                    .width(100.dp)
            )
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text = book.title ?: "",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        color = Color.DarkGray
                    )
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "by ${book.authors?.joinToString()}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.DarkGray
                    )
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "first published  ${book.firstPublishYear}  -- ${book.editionCount} editions",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
fun SearchResultPreview() {
    SearchResultScreen(
        screen = SearchResultScreen(
            query = "Maya Angelou",
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
            ),
            onBackPressed = {}
        )
    )
}
