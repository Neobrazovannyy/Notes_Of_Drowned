package com.example.notesofdrowned.navigation.navigationcontroller


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notesofdrowned.screens.notesminimalbybookmark.NotesMinimalByBookmark
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.screens.WN.WriteNote
import com.example.notesofdrowned.screens.notesminimal.NotesMinimal
import com.example.notesofdrowned.screens.editnote.EditNote
import com.example.notesofdrowned.screens.bookmarkerslibrary.BookmarkersLibrary
import com.example.notesofdrowned.screens.componentsNOD.ComponentsNOD

@Composable
fun NavigationControllerHost(navController: NavHostController, workDBArchMini: WorkDBArchMini) {
    var isFirstLoadListBookmarker by remember { mutableStateOf<Boolean>(true) }


    NavHost(navController = navController, startDestination = "LibraryNotesMinimal_LoadNotes") {
        /*----- Archive: Notes Minimal -----*/
        composable("LibraryNotesMinimal_LoadNotes") {
            NotesMinimal(navController, workDBArchMini, true)
        }
        composable("LibraryNotesMinimal") {
            NotesMinimal(navController, workDBArchMini, false)
        }
        /*----- Archive: Notes Minimal By Bookmarker -----*/
        composable("NotesMinimalByBookmarker/{colorBookmarker}") { backStackEntry ->
            val colorBookmarker = backStackEntry.arguments?.getString("colorBookmarker") ?: ""
            NotesMinimalByBookmark(navController, colorBookmarker)
        }
        /*----- Library Book -----*/
        composable("LibraryBook_LoadDB") {
            BookmarkersLibrary(navController, workDBArchMini, true, firstLoad={})
        }
        composable("LibraryBook") {
            BookmarkersLibrary(navController, workDBArchMini, isFirstLoadListBookmarker, firstLoad={isFirstLoadListBookmarker=false})
        }
        /*----- Write Note -----*/
        composable(
            route = "WriteNote?" +
                    "isSelectionAllowed={isSelectionAllowed}&" +
                    "defaultColorBookmarker={defaultColorBookmarker}",
            arguments = listOf(
                navArgument("isSelectionAllowed") { type = NavType.BoolType },
                navArgument("defaultColorBookmarker") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val isSelectionAllowed = backStackEntry.arguments?.getBoolean("isSelectionAllowed") ?: true
            val defaultColorBookmarker = backStackEntry.arguments?.getString("defaultColorBookmarker") ?: ComponentsNOD.defaultColorBookmarker

            WriteNote(navController, workDBArchMini, isSelectionAllowed, defaultColorBookmarker)
        }
        /*----- Edit Note -----*/
        composable(
            route="EditNote?" +
                    "idNote={idNote}&" +
                    "titleNote={titleNote}&" +
                    "textNote={textNote}&" +
                    "colorBookmarker={colorBookmarker}",
            arguments = listOf(
                navArgument("idNote") { type = NavType.StringType },
                navArgument("titleNote") { type = NavType.StringType },
                navArgument("textNote") { type = NavType.StringType },
                navArgument("colorBookmarker") { type = NavType.StringType }
            )
        ) { backStackEntry->
            val idNote = backStackEntry.arguments?.getString("idNote") ?: ""
            val titleNote = backStackEntry.arguments?.getString("titleNote") ?: ""
            val textNote = backStackEntry.arguments?.getString("textNote") ?: ""
            val colorBookmarker = backStackEntry.arguments?.getString("colorBookmarker") ?: ""

            EditNote(navController, workDBArchMini, idNote, titleNote, textNote, colorBookmarker)
        }
    }

}