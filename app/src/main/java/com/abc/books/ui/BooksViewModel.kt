package com.abc.books.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.abc.books.app.BooksApplication
import com.abc.books.data.repo.BooksRepository
import com.abc.books.data.models.Book
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BooksUiState {
    data class Success(
        val bookList: List<Book>,
        val selectedBook: Book,
        val topic: String,
        val canScrollDown: Boolean,
        val isShowingList: Boolean
    ) : BooksUiState

    data object Loading : BooksUiState
    data class Error(val message: String? = null) : BooksUiState
}

class BooksViewModel(private val booksRepository: BooksRepository, initialSearchQuery: String) :
    ViewModel() {

    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    private var startIndexPaginate: Int = 0

    init {
        getBooks(initialSearchQuery)
    }

    fun getBooks(searchQuery: String) {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading

            booksUiState = try {
                val books = booksRepository.getBooks(
                    searchQuery = searchQuery,
                    startIndex = startIndexPaginate
                )
                if (books.isEmpty()) BooksUiState.Error(searchQuery)
                else {
                    startIndexPaginate += books.size
                    BooksUiState.Success(books, books.first(), searchQuery, books.size == 40, true)
                }
            } catch (e: IOException) {
                BooksUiState.Error()
            } catch (e: HttpException) {
                BooksUiState.Error()
            }
        }
    }

    fun getBook(book: Book) {
        booksUiState =
            (booksUiState as BooksUiState.Success).copy(selectedBook = book, isShowingList = false)
    }

    fun goBack() {
        booksUiState = (booksUiState as BooksUiState.Success).copy(isShowingList = true)
    }

    companion object {
        fun getFactory(initialSearchQuery: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(
                    booksRepository = booksRepository,
                    initialSearchQuery = initialSearchQuery
                )
            }
        }
    }
}