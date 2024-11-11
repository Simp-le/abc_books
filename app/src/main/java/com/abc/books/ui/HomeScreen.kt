package com.abc.books.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abc.books.R
import com.abc.books.data.models.Book
import com.abc.books.app.theme.BooksTheme
import com.abc.books.data.DefaultDataProvider
import com.abc.books.ui.reusable.BookImage
import com.abc.books.ui.reusable.BooksAppBar
import com.abc.books.ui.reusable.ErrorScreen
import com.abc.books.ui.reusable.LoadingScreen
import com.abc.books.ui.reusable.Search


@Composable
fun HomeScreen(
    booksUiState: BooksUiState,
    retryAction: () -> Unit,
    onBookSelect: (Book) -> Unit,
    onBackButtonClick: () -> Unit,
    searchFunction: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    when (booksUiState) {
        is BooksUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is BooksUiState.Success -> SuccessScreen(
            isShowingListScreen = booksUiState.isShowingList,
            topic = booksUiState.topic,
            bookList = booksUiState.bookList,
            selectedBook = booksUiState.selectedBook,
            onItemClick = onBookSelect,
            onBackButtonClick = onBackButtonClick,
            windowSize = windowSize,
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .displayCutoutPadding(),
            paddingValues = paddingValues,
            searchFunction = searchFunction
        )

        is BooksUiState.Error -> ErrorScreen(retryAction = retryAction, message = booksUiState.message, modifier = modifier.fillMaxSize())
    }
}


/** The home screen displaying list of Amphibian object. */
@Composable
fun SuccessScreen(
    isShowingListScreen: Boolean,
    topic: String,
    bookList: List<Book>,
    selectedBook: Book,
    onItemClick: (Book) -> Unit,
    onBackButtonClick: () -> Unit,
    searchFunction: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact -> ContentType.ListOnly
        WindowWidthSizeClass.Medium -> ContentType.GridOnly
        WindowWidthSizeClass.Expanded -> ContentType.ListAndDetail
        else -> ContentType.ListOnly
    }

//    val pager = remember {
//        Pager(PagingConfig(pageSize = 20, enablePlaceholders = true,
//            maxSize = 20)) {
//            2 + 2
//        }
//    }

//    val lazyPagingItems = pager.flow.collectAsLazyPagingItem()

    if (contentType == ContentType.ListAndDetail)
        BookListDetail(
            bookList = bookList,
            book = selectedBook,
            topic = topic,
            onItemClick = onItemClick,
            searchFunction = searchFunction,
            modifier = modifier
        )
    else {
        if (isShowingListScreen) {
            if (contentType == ContentType.ListOnly) BookList(
                topic = topic,
                bookList = bookList,
                onItemClick = { onItemClick(it) },
                searchFunction = searchFunction,
                modifier = modifier
            ) else {
                BookGrid(
                    topic = topic,
                    bookList = bookList,
                    onItemClick = onItemClick,
                    searchFunction = searchFunction,
                    modifier = modifier,
                )
            }
        } else BookDetail(
            selectedBook = selectedBook,
            windowSize = windowSize,
            modifier = modifier,
            onBackButtonClick
        )
    }
}

@Composable
fun BookListDetail(
    bookList: List<Book>,
    book: Book,
    topic: String,
    onItemClick: (Book) -> Unit,
    searchFunction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        BookList(
            topic = topic,
            bookList = bookList,
            onItemClick = onItemClick,
            searchFunction = searchFunction,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = dimensionResource(R.dimen.padding_small))
        )
        BookDetail(
            selectedBook = book,
            windowSize = WindowWidthSizeClass.Expanded,
            modifier = Modifier.weight(3f)
        )
    }
}

@Composable
fun BookList(
    topic: String,
    bookList: List<Book>,
    onItemClick: (Book) -> Unit,
    searchFunction: (String) -> Unit,
    modifier: Modifier = Modifier,
    includeImage: Boolean = true
) {
    Scaffold(topBar = { BooksAppBar(onBackButtonClick = null) }, modifier = modifier) {
        LazyColumn(
            contentPadding = it,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            item {
                Search(
                    query = topic,
                    searchFunction = searchFunction,
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )
            }
            items(bookList, key = { book -> book.id }) { book ->
                BookItem(
                    book = book,
                    onItemClick = onItemClick,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxWidth(),
                    includeImage = includeImage
                )
            }
        }
    }
}

@Composable
fun BookGrid(
    topic: String,
    bookList: List<Book>,
    onItemClick: (Book) -> Unit,
    searchFunction: (String) -> Unit,
    modifier: Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        columns = GridCells.Fixed(2)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Search(
                query = topic,
                searchFunction = searchFunction,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }

        items(bookList, key = { book -> book.id }) { book ->
            BookItem(
                book = book,
                onItemClick = onItemClick,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun BookDetail(
    selectedBook: Book,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit = {}
) {
    BackHandler { onBackButtonClick() }

    Scaffold(
        topBar = { if (windowSize != WindowWidthSizeClass.Expanded) BooksAppBar(onBackButtonClick = onBackButtonClick) },
        modifier = modifier
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_medium)
                )
                .verticalScroll(rememberScrollState())
        ) {
            val textModifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxWidth()
            Text(
                selectedBook.title,
                style = MaterialTheme.typography.displaySmall,
                lineHeight = 40.sp,
                textAlign = if (windowSize == WindowWidthSizeClass.Expanded) TextAlign.Center else TextAlign.Start,
                modifier = textModifier
            )

            BookImage(
                source = selectedBook.imageUrl, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .clip(RoundedCornerShape(20.dp))
            )
            Text(
                selectedBook.authors.joinToString(", "),
                style = MaterialTheme.typography.titleLarge,
                modifier = textModifier
            )
            Text(
                selectedBook.publishedDate,
                style = MaterialTheme.typography.titleLarge,
                modifier = textModifier
            )

            Text(
                selectedBook.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = textModifier
            )

        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onItemClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
    includeImage: Boolean = true
) {
    val cornerRadius = 40.dp
    val contentPadding = dimensionResource(R.dimen.padding_extra_small)
    val cardShadowColor = Color(0xFF221300)
    Card(
        modifier = modifier
            .shadow(
                shape = RoundedCornerShape(cornerRadius),
                elevation = 10.dp,
                ambientColor = cardShadowColor,
                spotColor = cardShadowColor
            ), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = { onItemClick(book) }
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .clip(RoundedCornerShape(cornerRadius - contentPadding))
        ) {
            if (includeImage)
                BookImage(
                    source = book.imageUrl,
                    modifier = Modifier.height(500.dp),
                    doAnimation = true
                )

            BookItemInfo(
                book = book,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                isOnlyInfo = !includeImage
            )

        }
    }
}

@Composable
fun BookItemInfo(
    book: Book,
    modifier: Modifier = Modifier,
    isOnlyInfo: Boolean = false,
) {
    Column(
        modifier = modifier.padding(vertical = if (isOnlyInfo) 30.dp else 0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = book.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            textAlign = TextAlign.Center,
            style = if (isOnlyInfo) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.headlineSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        if (book.publishedDate.isNotEmpty() || book.authors.isNotEmpty()) Spacer(Modifier.height(6.dp))

        if (book.publishedDate.isNotEmpty()) Text(
            book.publishedDate,
            style = MaterialTheme.typography.titleMedium
        )
        if (book.authors.isNotEmpty()) Text(
            book.authors.joinToString(", "),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )
    }
}



@Preview
@Composable
fun PreviewBookItem() {
    BooksTheme {
        Surface {
            BookItem(
                book = DefaultDataProvider.book,
                onItemClick = { _ -> },
                modifier = Modifier.padding(50.dp)
            )
        }
    }
}