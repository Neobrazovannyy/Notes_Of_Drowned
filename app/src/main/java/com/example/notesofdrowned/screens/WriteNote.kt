package com.example.notesofdrowned.screens.WN

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
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
import com.example.notesofdrowned.ui.theme.TextNote
import com.example.notesofdrowned.ui.theme.TextNote1


@Composable
fun WriteNote(/* navController: NavHostController, workDBArchMini: WorkDBArchMini */) {
    var textInTitleField by remember { mutableStateOf("Миндалевидное тело (амигдала)") }
    var textInDirectionField by remember { mutableStateOf("Область мозга миндалевидной формы, находящаяся в белом веществе височной доли полушария под скорлупой, примерно на 1,5—2,0 см сзади от височного полюса. В мозге два миндалевидных тела — по одному в каждом полушарии. Миндалевидное тело играет ключевую роль в формировании эмоций, в частности страха. ") }
    val context = LocalContext.current //DELL

    Column(modifier=Modifier
        .fillMaxSize()
        .background(BgApp)
    ) {
        Spacer(modifier=Modifier.height(10.dp))

        Box(modifier=Modifier
            .height(67.dp)
            .fillMaxWidth()
            .background(BgApp),
            contentAlignment = Alignment.TopCenter
        ){
            Row(){
                Box(modifier = Modifier.weight(1f).height(67.dp)){
                    BasicTextField(
                        value = textInTitleField,
                        onValueChange = {textInTitleField=it},
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp, 0.dp),
                        textStyle = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            color = TextNote1,
                            fontSize = 28.sp,
                        ),
                        cursorBrush= SolidColor(BgNote),
                    )
                }
                Box(modifier=Modifier
                    .fillMaxHeight()
                    .width(25.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {Toast.makeText(context, "Нажато!", Toast.LENGTH_SHORT).show()}
                        )
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
                    fontSize = 16.sp,
                ),
                cursorBrush=SolidColor(BgNote),
            )
            Box(modifier = Modifier.fillMaxSize()){
                Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.BottomEnd){
                    Box(modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0XFF2A2A2B))
                        .clickable(){},
                        contentAlignment = Alignment.Center
                    )
                    {
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WriteNote()
}