package com.example.notesofdrowned.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesofdrowned.NoteObjectArea
import com.example.notesofdrowned.screens.LNWD.LibraryNotesWithDescription
import com.example.notesofdrowned.screens.LNM.LibraryNotesMinimal

@Composable
fun NavGraph(modifier: Modifier = Modifier, listNodesObjectArea: MutableList<NoteObjectArea>) {
    //создаем контроллер навигации
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LibraryNotesMinimal") {
        composable("LibraryNotesWithDescription") { //регистрируем экран с именем: "LibraryNotesWithDescription"
            LibraryNotesWithDescription(modifier, listNodesObjectArea)
        }
        composable("LibraryNotesMinimal") {
            LibraryNotesMinimal(modifier, listNodesObjectArea)
        }
    }
}

//            LibraryNotesWithDescription(onNavigateToProfile = {
//                navController.navigate("profile")
//            })