package com.abc.books

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.abc.books.ui.BooksApp
import com.abc.books.app.theme.BooksTheme

/**
 * Activity for Books app
 */
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowWidthSize = calculateWindowSizeClass(this).widthSizeClass

            BooksTheme {
                BooksApp(windowWidthSize)
            }
        }
    }
}