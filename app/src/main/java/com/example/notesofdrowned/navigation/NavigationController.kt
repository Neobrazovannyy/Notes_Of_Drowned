package com.example.notesofdrowned.navigation.navigationcontroller

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesofdrowned.ListNoteObjects
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.screens.WN.WriteNote
import com.example.notesofdrowned.screens.LNM.LibraryNotesMinimal
import com.example.notesofdrowned.screens.editnote.EditNote

@Composable
fun NavigationControllerHost(navController: NavHostController, listNoteObj: MutableList<ListNoteObjects>, workDBArchMini: WorkDBArchMini) {

    NavHost(navController = navController, startDestination = "LibraryNotesMinimal_LoadNotes") {
        composable("LibraryNotesMinimal_LoadNotes") {
            LibraryNotesMinimal(listNoteObj, navController, workDBArchMini, true)
        }
        composable("LibraryNotesMinimal") {
            LibraryNotesMinimal(listNoteObj, navController, workDBArchMini, false)
        }
        composable("WriteNote") {
            WriteNote(navController, workDBArchMini)
        }
        composable("EditNote/{idNote}/{titleNote}/{textNote}/{colorBookmarker}") { backStackEntry->
            val idNote = backStackEntry.arguments?.getString("idNote") ?: ""
            val titleNote = backStackEntry.arguments?.getString("titleNote") ?: ""
            val textNote = backStackEntry.arguments?.getString("textNote") ?: ""
            val colorBookmarker = backStackEntry.arguments?.getString("colorBookmarker") ?: ""

            EditNote(navController, workDBArchMini, idNote, titleNote, textNote, colorBookmarker)
        }
    }

}