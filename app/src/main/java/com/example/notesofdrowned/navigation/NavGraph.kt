package com.example.notesofdrowned.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesofdrowned.NodeObjectArea
import com.example.notesofdrowned.screens.LibraryNotesWithDescription
//import com.example.notesofdrowned.screens.ProfileScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, listNodesObjectArea: MutableList<NodeObjectArea>) {
    //создаем контроллер навигации
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LibraryNotesWithDe") {
        composable("LibraryNotesWithDe") { //регистрируем экран с именем: "LibraryNotesWithDe"
//            LibraryNotesWithDescription(onNavigateToProfile = {
//                navController.navigate("profile")
//            })
            LibraryNotesWithDescription(modifier, listNodesObjectArea)
        }
//        composable("profile") {
//            ProfileScreen()
//        }
    }
}