package com.example.notesofdrowned.screens.WN


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.navigation.NavHostController
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.ui.theme.BgApp
import com.example.notesofdrowned.ui.theme.BgNote
import com.example.notesofdrowned.ui.theme.BgNote1
import com.example.notesofdrowned.ui.theme.BgNoteTransparent
import com.example.notesofdrowned.ui.theme.TextNote
import com.example.notesofdrowned.ui.theme.TextNote1
import com.example.notesofdrowned.ui.theme.TextWarn


@Composable
fun WriteNote(navController: NavHostController, workDBArchMini: WorkDBArchMini) {
    // Value for DB
    var textInTitleField = remember {mutableStateOf("")}
    var textInDirectionField = remember {mutableStateOf("")}
    var colorBookmarker = remember {mutableStateOf<String>("464646")}
    // Value for style
    val fontSizeTitle = 28
    val fontSizeDirection = 16
    // Flags
    var showWindowForSelectBookmarker = remember {mutableStateOf(false)}


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
                    .padding(20.dp), contentAlignment = Alignment.BottomEnd){
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
                                workDBArchMini.insertNoteWithColor(
                                    textInTitleField.value,
                                    textInDirectionField.value,
                                    colorBookmarker.value
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

    if(showWindowForSelectBookmarker.value){
        WindowSelectBookmarker(showWindowForSelectBookmarker, colorBookmarker, workDBArchMini)
    }

}

@Composable
fun WindowSelectBookmarker(showWindowForSelectBookmarker: MutableState<Boolean>, colorBookmarkerForNote: MutableState<String>, workDBArchMini: WorkDBArchMini){
    var listBookmarkerColors: List<WorkDBArchMini.TableColorBookmarker> = workDBArchMini.getAllColor()
    var showWindowAddColor = remember {mutableStateOf<Boolean>(false)}
    var showWindowCorrectOrDell = remember {mutableStateOf<Boolean>(false)}
    var selectNameBookmarker by remember {mutableStateOf<String>("")}
    var selectColorBookmarker by remember {mutableStateOf<String>("")}


    Box(modifier=Modifier
        .fillMaxSize()
        .background(BgNoteTransparent)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color = BgNoteTransparent)
        ) { showWindowForSelectBookmarker.value = false },
        contentAlignment = Alignment.Center
    ){
        Box(modifier=Modifier
            .width(300.dp)
            .height(250.dp)
            .background(BgNote, RoundedCornerShape(5.dp))
            .border(1.dp, BgNoteTransparent, RoundedCornerShape(5.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color.Transparent)
            ){},
        ){
            Column(modifier=Modifier.fillMaxSize()){
                /*========== Box: "add new color" button ==========*/
                Box(modifier=Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(BgNote1, RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = BgNoteTransparent)
                    ) { showWindowAddColor.value = true }
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
                /*========== Box: select a color from the list ==========*/
                Box(modifier=Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center
                ){
                    Column {
                        listBookmarkerColors.forEach{ itemDBBookmarker ->
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp, 10.dp)
                                .drawBehind {
                                    drawLine(
                                        color = Color(0xFF000000 or (if(itemDBBookmarker.color=="464646") "2A2A2B" else  itemDBBookmarker.color).toLong(16)),
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 2.dp.toPx()
                                    )
                                },)
                            {
                                Row(horizontalArrangement = Arrangement.SpaceBetween){
                                    Text(
                                        text = "  ${itemDBBookmarker.nameColor}",
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = ripple(color = BgNoteTransparent)
                                            ) {
                                                colorBookmarkerForNote.value = itemDBBookmarker.color
                                                showWindowForSelectBookmarker.value = false
                                            },
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            color = TextNote1,
                                        ),
                                    )
                                    Box(modifier = Modifier
                                        .width(30.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = ripple(color = BgNoteTransparent)
                                        ) {
                                            selectNameBookmarker=itemDBBookmarker.nameColor
                                            selectColorBookmarker=itemDBBookmarker.color
                                            showWindowCorrectOrDell.value = true
                                        },
                                        contentAlignment=Alignment.BottomEnd){
                                        Text(
                                            text = "\u22EE",
                                            modifier = Modifier
                                                .padding(end = 5.dp),
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
            }
        }
    }

    if(showWindowAddColor.value){
        WindowAddNewColor(showWindowAddColor, showWindowForSelectBookmarker, colorBookmarkerForNote, workDBArchMini)
    }
    if(showWindowCorrectOrDell.value){
        WindowCorrectOrDell(showWindowCorrectOrDell, selectNameBookmarker, selectColorBookmarker, workDBArchMini)
    }

}

@Composable
fun WindowAddNewColor(showWindowAddColor: MutableState<Boolean>, showWindowForSelectBookmarker: MutableState<Boolean>, colorBookmarkerForNote: MutableState<String>, workDBArchMini: WorkDBArchMini){
    //----- Text in input field && Value for value in database
    var textNewNameColor by remember {mutableStateOf("")}
    var textNewColor by remember {mutableStateOf("")}
    //----- Value for checking the correct entered color
    var selectNewColor by remember {mutableStateOf(Color(0xFFC1C1C1))}
    //----- Flags
    var correctColor by remember {mutableStateOf(false)}
    var msgCorrectColor by remember {mutableStateOf(false)}


    Box(modifier=Modifier
        .fillMaxSize()
        .background(BgNoteTransparent)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color = BgNoteTransparent)
        ) { showWindowAddColor.value = false },
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
            Column(modifier = Modifier.fillMaxSize()){
                Box(modifier=Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.Center,){
                    Column(){
                        BasicTextField(
                            value = textNewNameColor,
                            onValueChange = { newText->
                                textNewNameColor=newText
                            },
                            modifier = Modifier
                                .width(130.dp)
                                .padding(10.dp)
                                .horizontalScroll(rememberScrollState())
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
                                if (textNewNameColor.isEmpty()) {
                                    Text(
                                        text = "NAME COLOR",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                innerTextField()
                            },
                            singleLine = true
                        )
                        BasicTextField(
                            value = textNewColor,
                            onValueChange = { newText->
                                textNewColor=newText.trim().trimIndent().uppercase()
                                selectNewColor = try {
                                    correctColor=true
                                    Color(0xFF000000 or newText.toLong(16))
                                } catch (e: NumberFormatException) {
                                    correctColor=false
                                    selectNewColor
                                }
                            },
                            modifier = Modifier
                                .width(130.dp)
                                .padding(10.dp)
                                .horizontalScroll(rememberScrollState())
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
                            },
                            singleLine = true
                        )
                    }
                }
                Box(modifier=Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(BgNote1, RoundedCornerShape(0.dp, 0.dp, 5.dp, 5.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = BgNoteTransparent)
                    ) {
                        if (correctColor) {
                            showWindowAddColor.value = false
                            showWindowForSelectBookmarker.value = false
                            colorBookmarkerForNote.value = textNewColor
                            workDBArchMini.insertBookmarker(
                                textNewNameColor.trim().trimIndent(),
                                textNewColor.uppercase()
                            )
                        } else {
                            msgCorrectColor = true
                        }
                    }
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

            if(msgCorrectColor){
                Box(modifier=Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                )
                {
                    Text(
                        text = "incorrect color",
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

@Composable
fun WindowCorrectOrDell(showThisWindow: MutableState<Boolean>, textOldNameBookmarker: String, textOldColorBookmarker: String, workDBArchMini: WorkDBArchMini){
    //----- Text in input field && Value for value in database
    var textNewNameColor by remember {mutableStateOf(textOldNameBookmarker)}
    var textNewColor by remember {mutableStateOf(textOldColorBookmarker)}
    //----- Value for checking the correct entered color
    var selectNewColor by remember {mutableStateOf(Color(0xFF000000 or textOldColorBookmarker.toLong(16)))}
    //----- Flags
    var checkedDell by remember { mutableStateOf(false) }
    var correctColor by remember {mutableStateOf(true)}
    var msgCorrectColor by remember {mutableStateOf(false)}


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
            Row(){
                /*========== Box: remove bookmarker ==========*/
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                ){
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
                        /*---------- Button (remove bookmarker) ----------*/
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
                                    workDBArchMini.delColorBookmarkerByColor(textOldColorBookmarker.uppercase())
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
                /*========== Box: update bookmarker ==========*/
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .drawBehind {
                        drawLine(
                            color = TextNote1,
                            start = Offset(0f, size.height),
                            end = Offset(0f, 0f),
                            strokeWidth = 2.dp.toPx()
                        )
                    },
                )
                {
                    Column(){
                        /*---------- TextField (update bookmarker) ----------*/
                        Box(modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                        ){
                            Column(modifier = Modifier.fillMaxSize(),
                                verticalArrangement=Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                BasicTextField(
                                    value = textNewNameColor,
                                    onValueChange = { newText->
                                        textNewNameColor=newText
                                    },
                                    modifier = Modifier
                                        .width(130.dp)
                                        .padding(10.dp)
                                        .horizontalScroll(rememberScrollState())
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
                                        if (textNewNameColor.isEmpty()) {
                                            Text(
                                                text = "NAME COLOR",
                                                color = Color.Gray,
                                                fontSize = 16.sp,
                                                textAlign = TextAlign.Center,
                                            )
                                        }
                                        innerTextField()
                                    },
                                    singleLine = true
                                )
                                BasicTextField(
                                    value = textNewColor,
                                    onValueChange = { newText->
                                        textNewColor=newText.trim().trimIndent().uppercase()
                                        selectNewColor = try {
                                            correctColor=true
                                            Color(0xFF000000 or newText.toLong(16))
                                        } catch (e: NumberFormatException) {
                                            correctColor=false
                                            selectNewColor
                                        }
                                    },
                                    modifier = Modifier
                                        .width(130.dp)
                                        .padding(10.dp)
                                        .horizontalScroll(rememberScrollState())
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
                                    },
                                    singleLine = true
                                )
                            }
                        }
                        if(msgCorrectColor){
                            Box(modifier=Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                            )
                            {
                                Text(
                                    text = "incorrect color",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        color = TextNote1,
                                        textAlign = TextAlign.Center,
                                    ),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }

                        /*---------- Button (update bookmarker) ----------*/
                        Box(modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .background(BgNote1, RoundedCornerShape(bottomEnd = 5.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = BgNoteTransparent)
                            ) {
                                if (correctColor) {
                                    Log.d("dbMu", "UPDATE from WN: $textNewNameColor::$textNewColor <= $textOldColorBookmarker")
                                    showThisWindow.value = false
                                    workDBArchMini.updateBookmarkerByColor(
                                        textNewNameColor.trim().trimIndent(),
                                        textNewColor.uppercase(),
                                        textOldColorBookmarker.uppercase()
                                    )
                                } else {
                                    msgCorrectColor = true
                                }
                            },
                            contentAlignment = Alignment.Center,

                        ){
                            Text(
                                text = "EDIT",
                                modifier = Modifier.padding(end=5.dp),
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
}

/*==================== parts of the constructor ====================*/
@Composable
fun InputFieldWithSubscript(modifier: Modifier, alignmentText: Alignment, textInField: MutableState<String>, fontSizeText: Int, textSubscript: String){
    BasicTextField(
        value = textInField.value,
        onValueChange = { newTextInField ->
            textInField.value=newTextInField.trim().trimIndent()
        },
        modifier = modifier.fillMaxWidth(),
        textStyle = TextStyle(
            fontFamily = FontFamily.SansSerif,
            color = TextNote1,
            fontSize = fontSizeText.sp,
        ),
        cursorBrush= SolidColor(TextNote1),
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
//    WindowCorrectOrDell()
}