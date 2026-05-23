package com.example.notesofdrowned.screens.editnote

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.screens.components.InputFieldWithSubscript
import com.example.notesofdrowned.screens.windowactionbookmarker.WindowSelectBookmarker
import com.example.notesofdrowned.ui.theme.BgApp
import com.example.notesofdrowned.ui.theme.BgNote
import com.example.notesofdrowned.ui.theme.BgNoteTransparent
import com.example.notesofdrowned.ui.theme.TextNote
import com.example.notesofdrowned.ui.theme.TextNote1
import com.example.notesofdrowned.ui.theme.TextWarn

@Composable
fun EditNote(navController: NavHostController, workDBArchMini: WorkDBArchMini, idNote: String, titleNote: String, textNote: String, colorBookmarker: String) {
    // Value for DB
    var textInTitleField = remember {mutableStateOf(titleNote)}
    var textInDirectionField = remember {mutableStateOf(textNote)}
    var colorBookmarker = remember {mutableStateOf<String>(colorBookmarker)}
    // Value for style
    val fontSizeTitle = 28
    val fontSizeDirection = 16
    // Flags
    var showWindowForSelectBookmarker = remember {mutableStateOf(false)}
    var showWindowDelNote = remember {mutableStateOf(false)}


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
                Box(modifier = Modifier
                    .weight(1f)
                    .height(67.dp), contentAlignment = Alignment.CenterStart){
                    InputFieldWithSubscript(
                        Modifier.padding(10.dp, 0.dp),
                        Alignment.CenterStart,
                        textInTitleField,
                        fontSizeTitle,
                        "Word..."
                    )
                }
                /*----- Box: UI bookmarker -----*/
                Box(modifier=Modifier
                    .fillMaxHeight()
                    .width(25.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = BgNoteTransparent)
                    ) {
                        showWindowForSelectBookmarker.value = true
                    },
                    contentAlignment = Alignment.CenterEnd
                ){
                    Box(modifier=Modifier
                        .fillMaxHeight()
                        .width(15.dp)
                        .background(
                            Color(0xFF000000 or colorBookmarker.value.toLong(16)),
                            RoundedCornerShape(5.dp, 0.dp, 0.dp, 5.dp)
                        ),
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
        Box(modifier=Modifier
            .weight(1f)
            .fillMaxSize()){
            InputFieldWithSubscript(
                Modifier.padding(10.dp),
                Alignment.TopStart,
                textInDirectionField,
                fontSizeDirection,
                "Definition, description of the word..."
            )
            Box(modifier = Modifier.fillMaxSize()){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp), contentAlignment = Alignment.BottomEnd
                ){
                    Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Box(modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color(0XFF2A2A2B))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = BgNoteTransparent)
                            ) { showWindowDelNote.value=true },
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "×",
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = TextNote,
                                    textAlign = TextAlign.Center,
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Box(modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color(0XFF2A2A2B))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = BgNoteTransparent)
                            ) {
                                if (textInTitleField.value != "" && textInDirectionField.value != "") {
                                    val idBookmarker = workDBArchMini.getIdBookmarkerByColor(colorBookmarker.value)
                                    workDBArchMini.updateNoteById(
                                        idNote.toLong(),
                                        textInTitleField.value.trim().trimIndent(),
                                        textInDirectionField.value.trim(),
                                        idBookmarker
                                    )
                                    navController.navigate("LibraryNotesMinimal_LoadNotes")
                                }
                            },
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "✓",
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = TextNote,
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

    if(showWindowForSelectBookmarker.value){
        WindowSelectBookmarker(showWindowForSelectBookmarker, colorBookmarker, workDBArchMini)
    }
    if(showWindowDelNote.value){
        WindowDelNote(showWindowDelNote, idNote, navController, workDBArchMini)
    }
}

//@Preview(showBackground = true)
@Composable
fun WindowDelNote(showThisWindow: MutableState<Boolean>, idNote: String, navController: NavHostController, workDBArchMini: WorkDBArchMini){
    var checkedDell by remember { mutableStateOf(false) }

    Box(modifier=Modifier
        .fillMaxSize()
        .background(BgNoteTransparent)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color = BgNoteTransparent)
        ) {showThisWindow.value=false},
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .width(300.dp)
            .height(250.dp)
            .background(BgNote, RoundedCornerShape(5.dp))
            .border(1.dp, BgNoteTransparent, RoundedCornerShape(5.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color.Transparent)
            ){},
        ) {
            Column(){
                /*---------- CheckBox (remove bookmarker) ----------*/
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                        Checkbox(
                            checked = checkedDell,
                            onCheckedChange = { checkedDell = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = TextWarn,
                                uncheckedColor = TextNote1,
                            )
                        )
                        Text(
                            text = "sure you want to delete?",
                            modifier = Modifier
                                .padding(end=5.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif,
                                color = TextNote1,
                                textAlign=TextAlign.Center,
                            ),
                        )
                    }
                }

                Box(modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(TextWarn, RoundedCornerShape(bottomStart = 5.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = BgNoteTransparent)
                    ) {
                        if(checkedDell){
                            showThisWindow.value = false
                            workDBArchMini.delNoteById(idNote.toLong())
                            navController.navigate("LibraryNotesMinimal_LoadNotes")
                        }
                    },
                    contentAlignment = Alignment.Center,
                ){
                    Text(
                        text = "DELETE",
                        modifier = Modifier
                            .padding(end=5.dp),
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