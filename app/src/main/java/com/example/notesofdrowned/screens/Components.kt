package com.example.notesofdrowned.screens.componentsNOD

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.notesofdrowned.ui.theme.BgNote
import com.example.notesofdrowned.ui.theme.TextNote1


object ComponentsNOD{
    val defaultColorBookmarker: String = "464646"
}


@Composable
fun InputFieldWithSubscript(modifier: Modifier, alignmentText: Alignment, textInField: MutableState<String>, fontSizeText: Int, textSubscript: String){
    val customTextSelectionColors = TextSelectionColors(
        handleColor = BgNote,
        backgroundColor = BgNote.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            value = textInField.value,
            onValueChange = { newTextInField ->
                textInField.value = newTextInField
            },
            modifier = modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                color = TextNote1,
                fontSize = fontSizeText.sp,
            ),
            cursorBrush = SolidColor(TextNote1),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = alignmentText)
                {
                    if (textInField.value.isEmpty()) {
                        Text(
                            text = "$textSubscript",
                            color = Color.Gray,
                            fontSize = fontSizeText.sp,
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
