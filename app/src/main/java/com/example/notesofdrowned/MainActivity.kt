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
                    content = { paddingValues -> SeedArchiveNodes(modifier = Modifier.padding(paddingValues))}
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

sealed class NodeObjectArea{
    object BoxEmpty : NodeObjectArea()
    data class BoxText(val text: String) : NodeObjectArea()
    data class BoxImg(val imageBitmap: ImageBitmap) : NodeObjectArea()
    data class BoxNode(val titleNode: String, val textNode: String, val colorBookmarker: Long) : NodeObjectArea()
}

data class CheckLineAndNodeObjectArea(
    var checkLine: Short,
    val listNodes: MutableList<NodeObjectArea>,
)

@Composable
fun SeedArchiveNodes(modifier: Modifier = Modifier){
    // Если что это НЕ БУДЕТ РАБОТАТЬ когда добавлю б/д !!!!!!!!!!!!!!!!!!!!!!!!!!!
    var checkAndAddNode: (CheckLineAndNodeObjectArea, NodeObjectArea)->Unit = { checkAndNodeObjectArea, nodesObjectArea ->
        checkAndNodeObjectArea.checkLine++

        if (checkAndNodeObjectArea.checkLine.toInt()==1)
        {
            checkAndNodeObjectArea.listNodes.add(nodesObjectArea)
            checkAndNodeObjectArea.listNodes.add(NodeObjectArea.BoxEmpty)
        }
        else if(checkAndNodeObjectArea.checkLine.toInt()==2)
        {
            checkAndNodeObjectArea.listNodes.removeAt(checkAndNodeObjectArea.listNodes.lastIndex)
            checkAndNodeObjectArea.listNodes.add(nodesObjectArea)
            checkAndNodeObjectArea.checkLine=0
        }
        else{
            checkAndNodeObjectArea.checkLine=0
        }
    }


    var nodeObjectArea: CheckLineAndNodeObjectArea=CheckLineAndNodeObjectArea(
        checkLine=0,
        listNodes=mutableListOf()
    )

    checkAndAddNode(nodeObjectArea, NodeObjectArea.BoxNode(
        "Когнитивная система",
        "когнитивная структура — система познания (человека), сложившаяся в сознании в результате становления характера, воспитания, обучения, наблюдения и размышления об окружающем мире.",
        0xFF07575b
    ))
    checkAndAddNode(nodeObjectArea, NodeObjectArea.BoxNode(
        "Когнитивная система jjjjjjj",
        "когнитивная структура — система познания (человека), сложившаяся в сознании в результате становления характера, воспитания, обучения, наблюдения и размышления об окружающем мире.",
        0xFF07575b
    ))
    checkAndAddNode(nodeObjectArea, NodeObjectArea.BoxImg(
        ImageBitmap.imageResource(R.drawable.baba_nyura),
    ))
    checkAndAddNode(nodeObjectArea, NodeObjectArea.BoxNode(
        "Антифон",
        "Антифон (гр. «звучащий в ответ; откликающийся, вторящий») — рефрен в католическом богослужении",
        0xFFC4dfe6
    ))
    checkAndAddNode(nodeObjectArea, NodeObjectArea.BoxNode(
        "Профанация",
        "Профанация — искажение, опошление чего-либо. В отличие от святотатства — осквернения умышленного, профанация, как правило, представляет собой действие невольное.",
        0xFFdb7e58
    ))
    checkAndAddNode(nodeObjectArea, NodeObjectArea.BoxText(
        "ЧИТАТЬ"
    ))
    checkAndAddNode(nodeObjectArea, NodeObjectArea.BoxNode(
        "Тремор",
        "Тремор (от лат. tremor, «дрожание») — непроизвольные быстрые ритмичные колебательные движения частей тела или всего тела.",
        0xFFdbae58
    ))

    ArchiveNodes(modifier, nodeObjectArea.listNodes)
}

@Composable
fun ArchiveNodes(modifier: Modifier = Modifier, listNodesObjectArea: MutableList<NodeObjectArea>) {
    /*--- For Logic---*/
    var lineElement: Array<NodeObjectArea> = arrayOf(NodeObjectArea.BoxEmpty, NodeObjectArea.BoxEmpty)
    var countNodeInLine: Int=0
    val maxNodeInLine: Short=2
    /*--- For Design---*/
    var modifierBoxPadding: Modifier = Modifier.height(90.dp).padding(5.dp)
    var modifierBoxNodes: Modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(5.dp))

    Box(modifier=modifier
        .fillMaxSize()
        .background(Color(colorBgApp))
        .verticalScroll(rememberScrollState())
    ){
        // Window with notes
        Column(modifier=modifier.fillMaxSize(), verticalArrangement=Arrangement.Bottom) {
            for (itemNode in listNodesObjectArea)
            {
                countNodeInLine++
                lineElement[countNodeInLine-1]=itemNode
                if(countNodeInLine >= maxNodeInLine)
                {
                    Row(modifier=Modifier.fillMaxWidth())
                    {
                        for(itemLineNode in lineElement){
                            Box(modifier=modifierBoxPadding.weight(1f)){
                                when(itemLineNode){
                                    is NodeObjectArea.BoxEmpty -> {/*...*/}
                                    is NodeObjectArea.BoxText -> {
                                        Box(modifier=modifierBoxNodes){
                                            Text(
                                                modifier = Modifier
                                                .fillMaxSize()
                                                .padding(top = 5.dp),
                                                text = "${itemLineNode.text}",
                                                style = TextStyle(
                                                    fontSize = 25.sp,
                                                    fontWeight = FontWeight.Light,
                                                    fontFamily = FontFamily.Monospace,
                                                    color = Color(colorTextNote),
                                                    letterSpacing = 3.sp,
                                                    textAlign = TextAlign.Center
                                                )
                                            )
                                        }
                                    }
                                    is NodeObjectArea.BoxImg -> {
                                        Box(modifier=modifierBoxNodes.verticalScroll(rememberScrollState())) {
                                            Image(
                                                bitmap = itemLineNode.imageBitmap,
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxSize(),
                                                alignment = Alignment.Center,
                                                contentScale = ContentScale.Crop,
                                            )
                                        }
                                    }
                                    is NodeObjectArea.BoxNode -> {
                                        Box(modifier=modifierBoxNodes){
                                            BlockNoteInArchive(itemLineNode.titleNode, itemLineNode.textNode, itemLineNode.colorBookmarker)
                                        }
                                    }
                                }
                            }

                        }
                    }
                    countNodeInLine=0
                }
            }
        }
        // The Button for adding a new node
        Column() { }
    }

}

@Composable
fun BlockNoteInArchive(titleNode: String, textNode: String, colorBookmarker: Long){
    val roundCornerBookmarker: RoundedCornerShape=RoundedCornerShape(0.dp,0.dp,10.dp, 10.dp)
    val context = LocalContext.current //DELL


    Box(modifier = Modifier.fillMaxSize().background(Color(colorBgNote)), contentAlignment = Alignment.BottomStart)
    {
        // BLOCK: Text
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start){
            Row(){
                Text(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(2f),
                    text = "$titleNode",
                    overflow = TextOverflow.Clip,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily.Monospace,
                        color = Color(colorTextNote),
                        letterSpacing = 3.sp,
                        textAlign = TextAlign.Start
                    )
                )
                Spacer(modifier = Modifier.width(35.dp))
            }
            Text(
                modifier = Modifier.padding(5.dp),
                text = "$textNode",
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
//                overflow = TextOverflow.Clip, //cut
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.Monospace,
                    color = Color(colorTextNote),
                    textAlign = TextAlign.Start
                )
            )
        }

        // BLOCK: Bookmarker
        Row(){
            Spacer(modifier = Modifier.weight(1f))
            Column() {
                // UI: Bookmarker
                Box(
                    modifier = Modifier
                        .padding(0.dp)
                        .requiredSize(50.dp, 40.dp)
                        .background(Color.Transparent)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    Toast.makeText(context, "Нажато!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                ){
                    Box(modifier=Modifier.fillMaxSize(), contentAlignment=Alignment.TopCenter){
                        Box(
                            modifier = Modifier
                            .width(20.dp)
                            .height(25.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(colorBgNote),
                                        Color(colorBookmarker)
                                    ),
                                    startY = -5f,
                                    endY = 50f,
                                    tileMode = TileMode.Clamp
                                ),
                                roundCornerBookmarker
                            ),
                            contentAlignment = Alignment.TopStart
                        ){}
                    }
                }
                Spacer(modifier = Modifier.fillMaxHeight())
            }
        }
    }

}

// @Preview indicates a preview (showBackground - this creates a background).
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesOfDrownedTheme {
        SeedArchiveNodes(Modifier.fillMaxSize())
    }
}