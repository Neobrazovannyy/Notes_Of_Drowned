package com.example.notesofdrowned

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.notesofdrowned.ui.theme.NotesOfDrownedTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ripple
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notesofdrowned.database.dbhelperarchmini.DBHelperArchMini
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.navigation.navigationcontroller.NavigationControllerHost
import com.example.notesofdrowned.ui.theme.BgNote1
import com.example.notesofdrowned.ui.theme.BgNoteTransparent
import com.example.notesofdrowned.ui.theme.TextNote


sealed class ListNoteObjects{
    object BoxEmpty : ListNoteObjects()
    data class BoxNote(val idNote: Long, val titleNote: String, val textNote: String, val colorBookmarker: String) : ListNoteObjects()
}

class MainActivity : ComponentActivity() {
    //Called by the system once at startup
    override fun onCreate(savedInstanceState: Bundle?) {
        var listNoteObj: MutableList<ListNoteObjects> = mutableListOf()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NotesOfDrownedTheme {
                Scaffold(
                    topBar= {MyTopBar()},
                    bottomBar={MyBottomBar(navController)},
                    modifier = Modifier.fillMaxSize(),
                    content = { paddingValues ->
                        LoadingDataAndNavigation(Modifier.padding(paddingValues), listNoteObj, navController)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = TextNote,
            containerColor = BgNote1,
        ),
        title = { Text("Заголовок") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(navController: NavHostController){
    val textButtonScreens: @Composable (String)-> Unit={nameButton->
        Text(
            text = "$nameButton",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = TextNote,
                textAlign = TextAlign.Center,
            ),
        )
    }

    BottomAppBar(
        contentColor=TextNote,
        containerColor = BgNote1,
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround){
            Box(Modifier
                .padding(top=15.dp)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(color = BgNoteTransparent),
                    onClick = {
                        navController.navigate("LibraryNotesMinimal")
                    },
                    onDoubleClick = {
                        navController.navigate("LibraryNotesMinimal_LoadNotes")
                    })
            ){textButtonScreens("ARCHIVE")}
            Box(Modifier
                .padding(top=15.dp)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(color = BgNoteTransparent),
                    onClick = {
//                        navController.navigate("LibraryNotesMinimal")
                    },
                    onDoubleClick = {
//                        navController.navigate("LibraryNotesMinimal_LoadNotes")
                    })
            ){textButtonScreens("LIBRARY")}
        }
    }
}

@Composable
fun LoadingDataAndNavigation(modifier: Modifier, listNoteObj: MutableList<ListNoteObjects>, navController: NavHostController){
    val context = LocalContext.current
    val workDBArchMini = remember {
        val dbHelper= DBHelperArchMini(context)
        WorkDBArchMini(dbHelper)
    }

    Box(modifier=modifier){
        NavigationControllerHost(navController, listNoteObj, workDBArchMini)
    }
}