package com.example.notesofdrowned.screens.WN

import android.graphics.Paint.Align
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesofdrowned.ui.theme.BgApp
import com.example.notesofdrowned.ui.theme.BgNote
import com.example.notesofdrowned.ui.theme.BgNote1
import com.example.notesofdrowned.ui.theme.BgNoteTransparent
import com.example.notesofdrowned.ui.theme.TextNote
import com.example.notesofdrowned.ui.theme.TextNote1


@Composable
fun WriteNote(/* navController: NavHostController, workDBArchMini: WorkDBArchMini */) {
//    val textInTitleField by remember { mutableStateOf("Миндалевидное тело (амигдала)") }
//    val textInDirectionField by remember { mutableStateOf("Область мозга миндалевидной формы, находящаяся в белом веществе височной доли полушария под скорлупой, примерно на 1,5—2,0 см сзади от височного полюса. В мозге два миндалевидных тела — по одному в каждом полушарии. Миндалевидное тело играет ключевую роль в формировании эмоций, в частности страха. ") }
    var textInTitleField by remember {mutableStateOf("")}
    var textInDirectionField by remember {mutableStateOf("")}
    val fontSizeTitle = 28.sp
    val fontSizeDirection = 16.sp
    var showWindowForSelectBookmarker by remember {mutableStateOf(false)}

    Column(modifier=Modifier
        .fillMaxSize()
        .background(BgApp)
    ) {
        /*========== Upper indentation for the "WORD" input field ==========*/
        Spacer(modifier=Modifier.height(10.dp))

        /*========== "WORD" input filed ==========*/
        Box(modifier=Modifier
            .height(67.dp)
            .fillMaxWidth()
            .background(BgApp),
            contentAlignment = Alignment.TopCenter
        ){
            Row(){
                /*----- Box: input filed for "WORD" -----*/
                Box(modifier = Modifier.weight(1f).height(67.dp), contentAlignment = Alignment.CenterStart){
                    BasicTextField(
                        value = textInTitleField,
                        onValueChange = {textInTitleField=it},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp),
                        textStyle = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            color = TextNote1,
                            fontSize = fontSizeTitle,
                        ),
                        cursorBrush= SolidColor(TextNote1),
                        decorationBox = { innerTextField ->
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart)
                            {
                                if (textInTitleField.isEmpty()) {
                                    Text(
                                        text = "Word...",
                                        color = Color.Gray,
                                        fontSize = fontSizeTitle,
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
                /*----- Box: UI bookmarker -----*/
                Box(modifier=Modifier
                    .fillMaxHeight()
                    .width(25.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color=BgNoteTransparent)
                    ) {
                        showWindowForSelectBookmarker=true
                    },
                    contentAlignment = Alignment.CenterEnd
                ){
                    Box(modifier=Modifier
                        .fillMaxHeight()
                        .width(15.dp)
                        .background(BgNote, RoundedCornerShape(5.dp, 0.dp, 0.dp, 5.dp)),
                    ){}
                }
            }
        }

        /*========== Lower indentation for the "WORD" input field ==========*/
        Box(modifier=Modifier
            .fillMaxWidth()
            .height(10.dp)
            .drawBehind {
                drawLine(
                    color = BgNote,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            }
        )

        /*========== "Definition" input filed ==========*/
        Box(modifier=Modifier.weight(1f).fillMaxSize()){
            BasicTextField(
                value = textInDirectionField,
                onValueChange = {textInDirectionField=it},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    color = TextNote1,
                    fontSize = fontSizeDirection,
                ),
                cursorBrush=SolidColor(TextNote1),
                decorationBox = { innerTextField ->
                    Box {
                        if (textInDirectionField.isEmpty()) {
                            Text(
                                text = "Definition, description of the word...",
                                color = Color.Gray,
                                fontSize = fontSizeDirection
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Box(modifier = Modifier.fillMaxSize()){
                Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.BottomEnd){
                    Box(modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0XFF2A2A2B))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color=BgNoteTransparent)
                        ){/* navController.navigate("LibraryNotesMinimal_LoadNotes") */},
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "✓",
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextNote,
                                textAlign = TextAlign.Center,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }

    }

    if(showWindowForSelectBookmarker){
        WindowSelectBookmarker()
    }

}

@Composable
fun WindowSelectBookmarker(){
    var showAddColor by remember {mutableStateOf(false)}

    Box(modifier=Modifier
        .fillMaxSize()
        .background(BgNoteTransparent)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color=BgNoteTransparent)
        ){showAddColor=false},
        contentAlignment = Alignment.Center
    ){
        Box(modifier=Modifier
            .width(300.dp)
            .height(250.dp)
            .background(BgNote,RoundedCornerShape(5.dp))
            .border(1.dp, BgNoteTransparent,RoundedCornerShape(5.dp)),
        ){
            Column(modifier=Modifier.fillMaxSize()){
                /*========== Box: "add color" button ==========*/
                Box(modifier=Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(BgNote1, RoundedCornerShape(5.dp,5.dp,0.dp,0.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color=BgNoteTransparent)
                    ){showAddColor=true}
                ){
                    Text(
                        text = "Add new color for bookmarker",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = TextNote1,
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                /*========== Box: select color ==========*/
                Box(modifier=Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center
                ){
                    Column {
                        repeat(10) { index ->
                            Text(
                                text = "Элемент ${index + 1}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp, 10.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = ripple(color=BgNoteTransparent)
                                    ) { /* действие */ }
                                    .drawBehind {
                                        drawLine(
                                            color = TextNote1,
                                            start = Offset(0f, size.height),
                                            end = Offset(size.width, size.height),
                                            strokeWidth = 2.dp.toPx()
                                        )
                                    },
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = TextNote1,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    if(!showAddColor){
        var textNewColor by remember {mutableStateOf("")}
        var selectNewColor by remember {mutableStateOf(Color(0xFFC1C1C1))}

        Box(modifier=Modifier
            .fillMaxSize()
            .background(BgNoteTransparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color=BgNoteTransparent)
            ){showAddColor=!showAddColor},
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier
                    .width(300.dp)
                    .height(250.dp)
                    .background(BgNote, RoundedCornerShape(5.dp))
                    .border(1.dp, BgNoteTransparent, RoundedCornerShape(5.dp)),
            ) {
                Column(modifier = Modifier.fillMaxSize()){
                    Box(modifier=Modifier
                        .fillMaxWidth()
                        .weight(1f),
                        contentAlignment = Alignment.Center,
                    ){
                        BasicTextField(
                            value = textNewColor,
                            onValueChange = { newText->
                                textNewColor=newText
                                selectNewColor = try {
                                    Color(0xFF000000 or newText.toLong(16))
                                } catch (e: NumberFormatException) {
                                    Color(0xFFC1C1C1)
                                }
                            },
                            modifier = Modifier
                                .width(120.dp)
                                .padding(10.dp)
                                .drawBehind {
                                    drawLine(
                                        color = selectNewColor,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 2.dp.toPx()
                                    )
                                },
                            textStyle = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                color = TextNote1,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            ),
                            cursorBrush=SolidColor(TextNote1),
                            decorationBox = { innerTextField ->
                                if (textNewColor.isEmpty()) {
                                    Text(
                                        text = "C1C1C1",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                    Box(modifier=Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(BgNote1, RoundedCornerShape(0.dp,0.dp,5.dp,5.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color=BgNoteTransparent)
                        ){}
                    ){
                        Text(
                            text = "ADD COLOR",
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontFamily = FontFamily.SansSerif,
                                color = TextNote1,
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
//    WriteNote()
    WindowSelectBookmarker()
}