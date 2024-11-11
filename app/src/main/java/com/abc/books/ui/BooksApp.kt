package com.abc.books.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abc.books.R
import com.abc.books.app.theme.BooksTheme

@Composable
fun BooksApp(windowSize: WindowWidthSizeClass) {
    val topic = stringArrayResource(R.array.book_topics).random()
    val booksViewModel: BooksViewModel = viewModel(
        factory = BooksViewModel.getFactory(initialSearchQuery = topic)
    )
    val getBooks: () -> Unit = { booksViewModel.getBooks(topic) }

    HomeScreen(
        booksUiState = booksViewModel.booksUiState,
        retryAction = getBooks,
        onBookSelect = { booksViewModel.getBook(it) },
        onBackButtonClick = { booksViewModel.goBack() },
        searchFunction = { booksViewModel.getBooks(it) },
        windowSize = windowSize,
        modifier = Modifier.fillMaxSize()
    )
}


@PreviewScreenSizes
@Composable
fun PreviewCompactBooksApp() {
    BooksTheme {
        val windowWidthSize = WindowWidthSizeClass.Compact
        BooksApp(windowWidthSize)
    }
}