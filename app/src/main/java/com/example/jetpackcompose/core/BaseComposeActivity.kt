package com.example.jetpackcompose.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

abstract class BaseComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Enable edge-to-edge drawing for the entire window
        enableEdgeToEdge()

        setContent {
            // 2. Apply the application theme consistently
            AppThemeWrapper {
                // Surface is often used as the top-level container to define the background color
                // and elevation according to the Material specification.
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // 3. Use Scaffold to manage layout structure (e.g., TopAppBar, FloatingActionButton)
                    Scaffold(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .systemBarsPadding(),
                        // CRITICAL for edge-to-edge: Prevent Scaffold from auto-consuming insets,
                        // allowing us to handle them manually below.
                        contentWindowInsets = WindowInsets(0.dp)
                    ) { innerPadding ->
// 5. Call the abstract function to provide the unique screen content
                        // 4. Root Container for Content
                        // Box is used for maximum flexibility (e.g., overlapping elements)
                        Box(
                            modifier = Modifier.Companion
                                // A. Apply padding from the Scaffold (e.g., if a BottomBar was used)
                                .padding(innerPadding)
                                // B. CRITICAL: Apply insets for status/navigation bars as padding
                                .systemBarsPadding()
                                .fillMaxSize()
                        ) {
                            // 5. Call the abstract function to provide the unique screen content
                            ScreenContent()
                        }
                        // Directly call the screen content with padding
                        /*ScreenContent(modifier = Modifier
                            .padding(innerPadding) // Scaffold padding (bottom bar, etc.)
                            .systemBarsPadding()  // status & navigation bars
                            .fillMaxSize()
                        )*/
                    }
                }
            }
        }
    }

    /**
     * Abstract function that must be implemented by concrete activities to provide the actual UI content.
     * The content placed here is automatically:
     * - Themed by YourAppTheme.
     * - Placed inside a Scaffold.
     * - Padded away from the Status Bar and Navigation Bar via .systemBarsPadding().
     *
     * To enable scrolling for the content, use the ScrollableScreenContent instead.
     */
    @Composable
    abstract fun ScreenContent()

    /*@Composable
    open fun ScreenContent(modifier: Modifier = Modifier) {

    }*/

    /**
     * Helper Composable for subclasses that need simple vertical scrolling.
     * Use this instead of manually implementing a Column with .verticalScroll().
     *
     * @param scrollState The ScrollState remembered by the Composable.
     */
    @Composable
    fun ScrollableScreenContent(
        scrollState: ScrollState,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .verticalScroll(scrollState),
            content = content
        )
    }

    /**
     * Wraps your app theme
     * Replace with your own MaterialTheme setup
     */
    @Composable
    open fun AppThemeWrapper(content: @Composable () -> Unit) {
        // Replace with your app theme
        MaterialTheme(
            content = content
        )
    }
}