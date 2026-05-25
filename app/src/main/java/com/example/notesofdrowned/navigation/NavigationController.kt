package com.example.notesofdrowned.navigation.navigationcontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesofdrowned.screens.notesminimalbybookmark.NotesMinimalByBookmark
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.screens.WN.WriteNote
import com.example.notesofdrowned.screens.notesminimal.NotesMinimal
import com.example.notesofdrowned.screens.editnote.EditNote
import com.example.notesofdrowned.screens.librarybook.LibraryBook

@Composable
fun NavigationControllerHost(navController: NavHostController, workDBArchMini: WorkDBArchMini) {
    var isFirstLoadListBookmarker by remember { mutableStateOf<Boolean>(true) }


    NavHost(navController = navController, startDestination = "LibraryNotesMinimal_LoadNotes") {
        /*----- Library Notes Minimal -----*/
        composable("LibraryNotesMinimal_LoadNotes") {
            NotesMinimal(navController, workDBArchMini, true)
        }
        composable("LibraryNotesMinimal") {
            NotesMinimal(navController, workDBArchMini, false)
        }
        /*----- Library Notes Minimal By Bookmarker -----*/
        composable("NotesMinimalByBookmarker/{colorBookmarker}") { backStackEntry ->
            val colorBookmarker = backStackEntry.arguments?.getString("colorBookmarker") ?: ""
            NotesMinimalByBookmark(navController, colorBookmarker)
        }
        /*----- Library Book -----*/
        composable("LibraryBook_LoadDB") {
            LibraryBook(navController, workDBArchMini, true, firstLoad={})
        }
        composable("LibraryBook") {
            LibraryBook(navController, workDBArchMini, isFirstLoadListBookmarker, firstLoad={isFirstLoadListBookmarker=false})
        }
        /*----- Write Note -----*/
        composable("WriteNote") {
            WriteNote(navController, workDBArchMini)
        }
        /*----- Edit Note -----*/
        composable("EditNote/{idNote}/{titleNote}/{textNote}/{colorBookmarker}") { backStackEntry->
            val idNote = backStackEntry.arguments?.getString("idNote") ?: ""
            val titleNote = backStackEntry.arguments?.getString("titleNote") ?: ""
            val textNote = backStackEntry.arguments?.getString("textNote") ?: ""
            val colorBookmarker = backStackEntry.arguments?.getString("colorBookmarker") ?: ""

            EditNote(navController, workDBArchMini, idNote, titleNote, textNote, colorBookmarker)
        }
    }

}