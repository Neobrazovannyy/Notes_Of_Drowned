package com.example.notesofdrowned.screens.LNM

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notesofdrowned.ListNoteObjects
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.ui.theme.BgApp
import com.example.notesofdrowned.ui.theme.BgNote
import com.example.notesofdrowned.ui.theme.BgNoteTransparent
import com.example.notesofdrowned.ui.theme.TextNote


val ComposLocalDB = compositionLocalOf<WorkDBArchMini> { error("No MyData provided") }

@Composable
fun LibraryNotesMinimal(listNoteObj: MutableList<ListNoteObjects>, navController: NavHostController, workDBArchMini: WorkDBArchMini, isLoadListObjNotes: Boolean) {
    if(isLoadListObjNotes){
        listNoteObj.clear()

        val listNotes: List<WorkDBArchMini.TableArchiveMini> = workDBArchMini.getAllNode()
        listNotes.forEach{ noteArchMini->
            listNoteObj.add(ListNoteObjects.BoxNote(
                noteArchMini.title,
                noteArchMini.description,
                noteArchMini.colorBookmarkerId
            ))
        }
    }

    CompositionLocalProvider(ComposLocalDB provides workDBArchMini){
        ArchiveNotes(listNoteObj, navController)
    }
}

@Composable
fun ArchiveNotes(listNoteObj: MutableList<ListNoteObjects>, navController: NavHostController) {
    /*--- For Logic---*/
    var countNoteInLine: Int=0
    val maxNoteInLine:Int=4
    val lineElement: Array<ListNoteObjects> = Array(size=maxNoteInLine){ListNoteObjects.BoxEmpty}

    //WINDOW with a notes
    Box(modifier=Modifier
        .fillMaxSize()
        .background(BgApp)
        .padding(3.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            for (itemNote in listNoteObj) {
                if(itemNote is ListNoteObjects.BoxNote){
                    countNoteInLine++
                    lineElement[countNoteInLine - 1] = itemNote

                    if (countNoteInLine==maxNoteInLine) {
                        DrowNotesInRow(lineElement)
                        countNoteInLine=0
                    }
                }
            }
            if(countNoteInLine!=0){
                for(i in countNoteInLine until maxNoteInLine){
                    lineElement[i]=ListNoteObjects.BoxEmpty
                }
                DrowNotesInRow(lineElement)
            }
        }

    }

    //WINDOW with the button for adding a new node
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
                ){
                    Log.d("Navigation", "Button clicked!")
                    try {
                        navController.navigate("WriteNote")
                        Log.d("Navigation", "Navigate called successfully")
                    } catch (e: Exception) {
                        Log.e("Navigation", "Error: ${e.message}")
                    }
                },
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = "+",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily.SansSerif,
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

@Composable
fun DrowNotesInRow(lineElement: Array<ListNoteObjects>){
    /*--- For Design---*/
    val modifierBoxPadding: Modifier = Modifier.height(70.dp).padding(3.dp)
    val modifierBoxNotes: Modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(5.dp))

    Row(modifier = Modifier.fillMaxWidth())
    {
        for (itemLineNote in lineElement)
        {
            Box(modifier = modifierBoxPadding.weight(1f)) {
                if(itemLineNote is ListNoteObjects.BoxNote){
                    Box(modifier=modifierBoxNotes){
                      BlockNoteInArchive(itemLineNote.titleNote, itemLineNote.textNote, itemLineNote.colorBookmarker)
                    }
                }
            }

        }
    }

}

@Composable
fun BlockNoteInArchive(titleNote: String, textNote: String, idBookmarker: Long){
    val roundCornerBookmarker=RoundedCornerShape(10.dp)
    val workDBArchMini = ComposLocalDB.current
    val colorBookmarker: String = workDBArchMini.getColorBookmarker(idBookmarker) ?: "464646"


    Box(modifier = Modifier.fillMaxSize().background(BgNote)) {
        Row(verticalAlignment=Alignment.CenterVertically)
        {
            // UI Text
            Box(modifier = Modifier.padding(5.dp).weight(1f)){
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "$titleNote",
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily.SansSerif,
                        color = TextNote,
                        textAlign = TextAlign.Start
                    )
                )
            }
            // UI Bookmarker
            Box(modifier = Modifier
                .padding(start = 1.dp)
                .width(3.dp)
                .height(40.dp)
                .background(color=Color(0xFF000000 or colorBookmarker.toLong(16)), shape=roundCornerBookmarker),
            ){}
        }
    }

}
