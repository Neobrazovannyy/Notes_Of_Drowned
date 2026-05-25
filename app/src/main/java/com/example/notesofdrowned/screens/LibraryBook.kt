package com.example.notesofdrowned.screens.bookmarkerslibrary

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notesofdrowned.ListNoteObjects
import com.example.notesofdrowned.LocalListBookmarker
import com.example.notesofdrowned.LocalListNote
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.ui.theme.BgApp
import com.example.notesofdrowned.ui.theme.BgNote
import com.example.notesofdrowned.ui.theme.BgNoteTransparent
import com.example.notesofdrowned.ui.theme.TextNote


@Composable
fun BookmarkersLibrary(navController: NavHostController, workDBArchMini: WorkDBArchMini, isLoadListObjNotes: Boolean, firstLoad: ()-> Unit){
    firstLoad()
    val listBookmarker = LocalListBookmarker.current

    if(isLoadListObjNotes){
        Log.d("dbMu", "updater(LibraryBook)")
        listBookmarker.clear()
        listBookmarker.addAll(workDBArchMini.getAllColor())
    }

    DrowBookmarkersLibrary(listBookmarker, navController)
}

@Composable
fun DrowBookmarkersLibrary(listBookmarker: MutableList<WorkDBArchMini.TableColorBookmarker>, navController: NavHostController){
    Box(modifier=Modifier
        .fillMaxSize()
        .background(BgApp)
        .padding(3.dp)
        .verticalScroll(rememberScrollState())
    ){
        Column() {
            listBookmarker.forEach { itemBookmarker->
                Box(modifier=Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp)
                    .padding(2.dp)
                    .border(
                        2.dp,
                        Color(0xFF000000 or itemBookmarker.color.toLong(16)),
                        RoundedCornerShape(5.dp)
                    )
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = BgNoteTransparent),
                        onClick = {
                            navController.navigate("NotesMinimalByBookmarker/${itemBookmarker.color}")
                        })
                ){
                    Text(
                        text = "${itemBookmarker.nameColor}",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.SansSerif,
                            color = TextNote,
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }
}
