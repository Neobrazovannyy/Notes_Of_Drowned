package com.example.notesofdrowned.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesofdrowned.NodeObjectArea
import com.example.notesofdrowned.colorBgApp
import com.example.notesofdrowned.colorBgNote
import com.example.notesofdrowned.colorTextNote

@Composable
fun LibraryNotesWithDescription(modifier: Modifier = Modifier, listNodesObjectArea: MutableList<NodeObjectArea>) {
    ArchiveNodes(modifier, listNodesObjectArea)
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
