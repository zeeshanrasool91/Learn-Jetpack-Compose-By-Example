package com.example.jetpackcompose.core

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    fun applyWindowsInsetListener(view: View){
        // 2. Set the listener to apply the system bar insets as padding.
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            // Get the insets for the system bars (status bar and navigation bar)
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply these insets as padding to your view.
            // This ensures your content isn't under the system bars.
            v.updatePadding(
                left = insets.left,
                top = insets.top,
                right = insets.right,
                bottom = insets.bottom
            )
            // Return the insets to indicate they have been consumed (applied as padding).
            WindowInsetsCompat.CONSUMED
        }
    }
}