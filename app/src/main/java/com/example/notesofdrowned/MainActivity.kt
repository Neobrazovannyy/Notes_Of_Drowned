package com.example.notesofdrowned

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesofdrowned.ui.theme.NotesOfDrownedTheme
import androidx.compose.material3.Button
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.collection.mutableObjectListOf
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.input.pointer.pointerInput
import com.example.notesofdrowned.navigation.NavGraph

/*
"Views" — are the widgets that Android is built on and that are displayed on the screen (buttons, text, input fields).
"Compose" — is a completely different engine. It doesn't have a "View" inside.
"ComposeView" — is a "View" (widget) for Android that runs the entire "Compose" engine.
*/
class MainActivity : ComponentActivity() { //Entry point to the UI process
    //Called by the system once at startup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() //Принудительно растягивает контент под системные бары
        setContent { //Here configure "Compose"
            // <NameProject>Theme — this "CompositionLocalProvider", it is an inject into context (Colors, Fonts, Shapes)
            NotesOfDrownedTheme {
                // Scaffold - this container; fillMaxSize - takes up the entire screen.
                /* Option 2 (Scaffold):
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding -> Greeting(...) }
                )
                //"Scaffold" calculates the fill size
                // taking into account: "StatusBar", "TopAppBar", "NavigationBar"
                // and passes the value to the lambda code
                */
                Scaffold(
                    topBar= {MyTopBar()},
                    bottomBar={MyBottomBar()},
                    modifier = Modifier.fillMaxSize(),
                    content = { paddingValues -> SeedArchiveNotes(modifier = Modifier.padding(paddingValues))}
                )
            }
        }
    }
}

/*----- Color -----*/
public const val colorBgApp=0xFF141414
public const val colorBgNote=0xFF464646
public const val colorBgNote_1=0XFF2A2A2B
public const val colorTextNote=0xFFEDEDED

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color(colorTextNote),
            containerColor = Color(colorBgNote_1),
        ),
        title = { Text("Заголовок") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(){
    BottomAppBar(
        contentColor=Color(colorTextNote),
        containerColor = Color(colorBgNote_1),
    ) {
        Text("Нижний бар")
    }
}

sealed class NoteObjectArea{
    object BoxEmpty : NoteObjectArea()
    data class BoxText(val text: String) : NoteObjectArea()
    data class BoxImg(val imageBitmap: ImageBitmap) : NoteObjectArea()
    data class BoxNote(val titleNote: String, val textNote: String, val colorBookmarker: Long) : NoteObjectArea()
}

@Composable
fun SeedArchiveNotes(modifier: Modifier = Modifier){ //?SeedArchiveNotes_LNWD
    // Если что это НЕ БУДЕТ РАБОТАТЬ когда добавлю б/д !!!!!!!!!!!!!!!!!!!!!!!!!!!
    var nodeObjectArea: MutableList<NoteObjectArea> = mutableListOf(NoteObjectArea.BoxNote(
        "Когнитивная система",
        "когнитивная структура — система познания (человека), сложившаяся в сознании в результате становления характера, воспитания, обучения, наблюдения и размышления об окружающем мире.",
        0xFF07575b
    ))

    nodeObjectArea.add(NoteObjectArea.BoxNote(
        "Когнитивная система jjjjjjj",
        "когнитивная структура — система познания (человека), сложившаяся в сознании в результате становления характера, воспитания, обучения, наблюдения и размышления об окружающем мире.",
        0xFF07575b
    ))
    nodeObjectArea.add(NoteObjectArea.BoxImg(
        ImageBitmap.imageResource(R.drawable.baba_nyura),
    ))
    nodeObjectArea.add(NoteObjectArea.BoxNote(
        "Антифон",
        "Антифон (гр. «звучащий в ответ; откликающийся, вторящий») — рефрен в католическом богослужении",
        0xFFC4dfe6
    ))
    nodeObjectArea.add(NoteObjectArea.BoxNote(
        "Профанация",
        "Профанация — искажение, опошление чего-либо. В отличие от святотатства — осквернения умышленного, профанация, как правило, представляет собой действие невольное.",
        0xFFdb7e58
    ))
    nodeObjectArea.add(NoteObjectArea.BoxText(
        "ЧИТАТЬ"
    ))
    nodeObjectArea.add(NoteObjectArea.BoxNote(
        "Тремор",
        "Тремор (от лат. tremor, «дрожание») — непроизвольные быстрые ритмичные колебательные движения частей тела или всего тела.",
        0xFFdbae58
    ))

    NavGraph(modifier, nodeObjectArea)
}

// @Preview indicates a preview (showBackground - this creates a background).
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesOfDrownedTheme {
        SeedArchiveNotes(Modifier.fillMaxSize())
    }
}