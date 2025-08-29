package com.beatrice.bookflow.presentation.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beatrice.bookflow.R
import com.squareup.workflow1.ui.TextController
import com.squareup.workflow1.ui.compose.ComposeScreen
import com.squareup.workflow1.ui.compose.asMutableTextFieldValueState

internal val OPTIONS = listOf("All", "Title", "Author", "Text", "Subject", "Lists")

data class SearchScreen(
    val message: String? = null,
    val searchByOptions: List<String> = OPTIONS,
    val searchBy: TextController = TextController(initialValue = OPTIONS[0]),
    val query: TextController = TextController(),
    val onSearchTapped: () -> Unit = {}
) : ComposeScreen {

    @Composable
    override fun Content() {
        Content(this)
    }
}

@Composable
private fun Content(
    screen: SearchScreen,
    modifier: Modifier = Modifier,
) {
    var selectedOption = screen.searchBy.asMutableTextFieldValueState()
    var query = screen.query.asMutableTextFieldValueState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(R.string.bookflow),
            style = TextStyle(
                fontSize = 27.sp,
                fontWeight = FontWeight.W700,
                fontStyle = FontStyle.Italic
            )
        )

        Spacer(Modifier.height(4.dp))

        Text(
            modifier = Modifier,
            text = stringResource(R.string.search_books),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic
            )
        )

        Spacer(Modifier.height(84.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            screen.message?.let {
                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterStart),
                    text = it,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Normal,
                        color = Color.Gray
                    )
                )

            }
        }
        Spacer(Modifier.height(8.dp))
        SearchRow(
            selectedOption = selectedOption.value,
            onSelectedOptionChanged = { option ->
                selectedOption.value = option
            },
            query = query.value,
            onQueryChanged = { newQuery ->
                query.value = newQuery
            },
            options = screen.searchByOptions
        )

        Spacer(Modifier.height(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = screen.onSearchTapped
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                text = "Search"
            )
        }
    }
}


@Composable
fun SearchRow(
    modifier: Modifier = Modifier,
    selectedOption: TextFieldValue,
    onSelectedOptionChanged: (TextFieldValue) -> Unit,
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    options: List<String>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .border(width = 1.5.dp, shape = RoundedCornerShape(16.dp), color = Color.LightGray)
    ) {
        SearchOptionsMenu(
            modifier = Modifier.weight(1f),
            selectedOption = selectedOption,
            onSelectedOptionChanged = onSelectedOptionChanged,
            options = options
        )
        VerticalDivider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.5.dp)
        )

        SearchField(
            modifier = Modifier.weight(2f),
            query = query,
            onQueryChanged = onQueryChanged
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SearchOptionsMenu(
    modifier: Modifier = Modifier,
    selectedOption: TextFieldValue,
    onSelectedOptionChanged: (TextFieldValue) -> Unit,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                bottomStart = 16.dp
            ),
            readOnly = true,
            maxLines = 1,
            trailingIcon = {
                Icon(
                    imageVector = if (!expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                    tint = Color.Black,
                    contentDescription = null
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.DarkGray,
            )
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        onSelectedOptionChanged(TextFieldValue(option))
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit
) {

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        maxLines = 1,
        modifier = modifier,
        shape = RoundedCornerShape(
            topEnd = 16.dp,
            bottomEnd = 16.dp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.DarkGray,
        )
    )
}
