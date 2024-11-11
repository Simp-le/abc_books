package com.abc.books.ui.reusable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abc.books.R

@Composable
fun Search(query: String, searchFunction: (String) -> Unit, modifier: Modifier = Modifier) {
    var isSearching by remember { mutableStateOf(false) }
    var currentQuery by remember { mutableStateOf("") }

    val search = {
        if (isSearching && currentQuery != query && currentQuery.isNotEmpty()) {
            searchFunction(currentQuery)
            isSearching = false
            currentQuery = ""
        }
    }

    val searchBarWidth by animateFloatAsState(
        targetValue = if (isSearching) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = modifier.height(75.dp)
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(RoundedCornerShape(8.dp))
            .padding(
                horizontal = dimensionResource(R.dimen.padding_small),
                vertical = dimensionResource(R.dimen.padding_extra_small)
            ),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = if (!isSearching) Arrangement.SpaceBetween else Arrangement.End
    ) {
        if (isSearching)
            SearchBar(search = search, query = currentQuery, onValueChange = { currentQuery = it }, modifier = Modifier.fillMaxWidth(searchBarWidth))
        else Text(
            text = query,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (!isSearching)
            IconButton(onClick = {
                isSearching = !isSearching
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    search: () -> Unit,
    query: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = stringResource(R.string.search),
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    fontColorUnfocused: Color = MaterialTheme.colorScheme.onSurface
) {
    val colors = TextFieldDefaults.colors(
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
    )

    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }


    BasicTextField(
        value = query,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier
            .height(42.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .onKeyEvent {
                if (it.type == KeyEventType.KeyDown && it.key == Key.Enter) {
                    focusManager.clearFocus()
                    true
                } else {
                    false
                }
            },
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
//                focusManager.clearFocus()
                search()
            }
        ),
    ) {
        TextFieldDefaults.DecorationBox(
            value = query,
            innerTextField = it,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                if (!isFocused && query.isEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.alignBy(LastBaseline)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            placeholderText,
                            modifier = Modifier.alignBy(LastBaseline),
                            fontStyle = textStyle.fontStyle,
                            color = fontColorUnfocused
                        )
                    }
                }
            },
            leadingIcon =
            if (isFocused || query.isNotEmpty()) {
                {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            } else null,
            interactionSource = interactionSource,
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 12.dp,
                end = 12.dp,
                top = 0.dp,
                bottom = 0.dp
            ),
            shape = RoundedCornerShape(50),
            colors = colors
        )
    }
}
