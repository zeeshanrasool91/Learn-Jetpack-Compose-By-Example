package com.example.jetpackcompose.material

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.core.Amenity
import com.example.jetpackcompose.core.BaseComposeActivity
import com.example.jetpackcompose.core.getAmenityList
import com.example.jetpackcompose.image.TitleComponent

class FlowRowActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This is an extension function of Activity that sets the @Composable function that's
        // passed to it as the root view of the activity. This is meant to replace the .xml file
        // that we would typically set using the setContent(R.id.xml_file) method. The setContent
        // block defines the activity's layout.
    }

    @Composable
    override fun ScreenContent() {
        // Column is a composable that places its children in a vertical sequence. You
        // can think of it similar to a LinearLayout with the vertical orientation.
        Column {
            TitleComponent(title = "Tap to select options")
            SimpleFlowRowSingleSelect(getAmenityList())
            SimpleFlowRow(getAmenityList())
        }
    }
}

// NOTE: FlowRow API was removed by the Compose team. Commenting it out so that we can compile the
// repo.

// We represent a Composable function by annotating it with the @Composable annotation. Composable
// functions can only be called from within the scope of other composable functions. We should 
// think of composable functions to be similar to lego blocks - each composable function is in turn 
// built up of smaller composable functions.
@Composable
fun SimpleFlowRowOld(amenityList: List<Amenity>) {
    // Reacting to state changes is the core behavior of Compose. We use the state composable
    // that is used for holding a state value in this composable for representing the current
    // value of whether the checkbox is checked. Any composable that reads the value of "selectedIndices"
    // will be recomposed any time the value changes. This ensures that only the composables that
    // depend on this will be redraw while the rest remain unchanged. This ensures efficiency and
    // is a performance optimization. It is inspired from existing frameworks like React.
//    val selectedIndices = mutableStateListOf<Int>()
    // Column is a composable that places its children in a vertical sequence. You
    // can think of it similar to a LinearLayout with the vertical orientation. 
    // In addition we also pass a few modifiers to it.

    // You can think of Modifiers as implementations of the decorators pattern that are used to
    // modify the composable that its applied to. In the example below, we configure the
    // Column to occupy the entire available width using the Modifier.fillMaxSize() modifier and 
    // also give it a padding of 4 dp.
    Column(modifier = Modifier.padding(4.dp).fillMaxSize()) {
        // FlowRow is a pre-defined composable that places its children in a horizontal flow 
        // similar to the Row composable. However, its different from the Row composable in that if 
        // the horizontal space is not sufficient for all the children in one row, it 
        // overflows to more rows. 
        // mainAxisAlignment is the alignment in the horizontal direction
        // crossAxisSpacing is the spacing between rows in the vertical direction
        // mainAxisSpacing is the spacing between the children in the same row

//        FlowRow(
//            mainAxisAlignment = MainAxisAlignment.Center,
//            crossAxisSpacing = 16.dp,
//            mainAxisSpacing = 16.dp,
//            mainAxisSize = SizeMode.Expand
//        ) {
//            amenityList.forEachIndexed { index, amenity ->
//                // Box with clickable modifier wraps the child composable and enables it to react to
//                // a click through the onClick callback similar to the onClick listener that we are
//                // accustomed to on Android.
//                // Here, we just add the current index to the selectedIndices set every
//                // time a user taps on it.
//                Box(Modifier.clickable(onClick = { selectedIndices.add(index) }), children = {
//                    // Text is a predefined composable that does exactly what you'd expect it to -
//                    // display text on the screen. It allows you to customize its appearance using
//                    // style, fontWeight, fontSize, etc.
//                    Text(
//                        text = if (selectedIndices.contains(index)) "✓ ${amenity.name}" else amenity.name,
//                        overflow = TextOverflow.Ellipsis,
//                        modifier = Modifier.drawBackground(
//                            color = colors[index % colors.size], shape = RoundedCornerShape(15.dp)) +
//                                Modifier.padding(8.dp)
//                    )
//                })
//            }
//        }
    }
}

// NOTE: Define your data class for Amenity, or replace it with your actual type.
// data class Amenity(val name: String)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SimpleFlowRow(amenityList: List<Amenity>) {
    // 1. State Management: Use remember { mutableStateSetOf() } for observable selection state.
    val selectedIndices = remember { mutableStateSetOf<Int>() }

    // 2. Sample Colors: Define a list of colors internally for the chip backgrounds.
    val colors = remember {
        listOf(
            Color(0xFFB39DDB), // Light Purple
            Color(0xFF81D4FA), // Light Blue
            Color(0xFFC8E6C9), // Light Green
            Color(0xFFFFCC80), // Light Orange
        )
    }

    // Column is used for arranging content vertically.
    Column(
        modifier = Modifier
            .padding(8.dp) // Increased padding slightly for better spacing
    ) {

        // FlowRow is used for a horizontal flow that wraps content to the next line.
        FlowRow(
            // Use standard Arrangement for alignment
            // Replaces 'mainAxisAlignment = Arrangement.Center' AND 'mainAxisSpacing = 16.dp'
            // This centers the items on the line and adds 16.dp spacing between them horizontally.
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            // Replaces 'crossAxisSpacing = 16.dp'
            // This adds 16.dp spacing between the different rows vertically.
            verticalArrangement = Arrangement.spacedBy(16.dp),
            // NOTE: 'mainAxisSize = SizeMode.Expand' is a deprecated/removed property.
            // FlowRow naturally fills the width of its parent.
        ) {
            amenityList.forEachIndexed { index, amenity ->
                val isSelected = selectedIndices.contains(index)
                val backgroundColor = colors[index % colors.size]

                // Box is used to apply the background, padding, and click handler
                Box(
                    // Chain modifiers in the correct order:
                    // 1. Apply visual styling (background and shape)
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(15.dp)
                        )
                        // 2. Apply padding for inner text spacing
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        // 3. Apply the clickable modifier LAST to ensure the whole chip is clickable
                        .clickable(onClick = {
                            // Toggle selection logic
                            if (isSelected) {
                                selectedIndices.remove(index)
                            } else {
                                selectedIndices.add(index)
                            }
                        })
                ) {
                    // Text Composable
                    Text(
                        text = if (isSelected) "✓ ${amenity.name}" else amenity.name,
                        overflow = TextOverflow.Ellipsis
                        // No need for any additional modifiers here since background and padding
                        // are applied to the parent Box.
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SimpleFlowRowSingleSelect(amenityList: List<Amenity>) {
    // Single-Select State: Holds the index of the currently selected item, or null if none.
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    // Internal Color definitions
    val SelectedChipColor = Color(0xFF673AB7) // Dark Purple
    val UnselectedChipColor = Color(0xFFE0E0E0) // Light Gray
    val SelectedTextColor = Color.White
    val UnselectedTextColor = Color.Black

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        FlowRow(
            // Horizontal spacing between chips and centering them on the line
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            // Vertical spacing between rows
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            amenityList.forEachIndexed { index, amenity ->
                val isSelected = selectedIndex == index

                val backgroundColor = if (isSelected) SelectedChipColor else UnselectedChipColor
                val textColor = if (isSelected) SelectedTextColor else UnselectedTextColor

                Box(
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .clickable(onClick = {
                            // Single-Select Logic: Toggle selection
                            selectedIndex = if (isSelected) null else index
                        })
                ) {
                    // Use a Row to hold the checkmark and the text side-by-side
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1. Fixed-Width Checkmark Placeholder
                        // This reserves space for the '✓' when selected, preventing the chip from moving.
                        Text(
                            text = if (isSelected) "✓" else "",
                            color = textColor,
                            modifier = Modifier.width(20.dp) // Fixed width for stability
                        )

                        // 2. Amenity Name Text
                        Text(
                            text = amenity.name,
                            color = textColor,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

/**
 * Android Studio lets you preview your composable functions within the IDE itself, instead of
 * needing to download the app to an Android device or emulator. This is a fantastic feature as you
 * can preview all your custom components(read composable functions) from the comforts of the IDE.
 * The main restriction is, the composable function must not take any parameters. If your composable
 * function requires a parameter, you can simply wrap your component inside another composable
 * function that doesn't take any parameters and call your composable function with the appropriate
 * params. Also, don't forget to annotate it with @Preview & @Composable annotations.
 */
@Preview
@Composable
fun SimpleFlowRowPreview() {
    SimpleFlowRow(getAmenityList())
}
