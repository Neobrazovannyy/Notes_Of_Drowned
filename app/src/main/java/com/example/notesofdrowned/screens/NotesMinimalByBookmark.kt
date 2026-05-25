package com.example.notesofdrowned.screens.notesminimalbybookmark

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.notesofdrowned.ListNoteObjects
import com.example.notesofdrowned.LocalListNote
import com.example.notesofdrowned.screens.archivenotes.ArchiveNotes


@Composable
fun NotesMinimalByBookmark(navController: NavHostController, selectColorBookmarker: String){
    val listNote = LocalListNote.current

    val listNoteByBookmarker: MutableList<ListNoteObjects> = mutableListOf()

    listNote.forEach { itemNote ->
        if(itemNote is ListNoteObjects.BoxNote){
            if(itemNote.colorBookmarker.equals(selectColorBookmarker, ignoreCase = true)){
                listNoteByBookmarker.add(ListNoteObjects.BoxNote(
                    idNote = itemNote.idNote,
                    titleNote = itemNote.titleNote,
                    textNote = itemNote.textNote,
                    colorBookmarker = itemNote.colorBookmarker
                ))
            }
        }
    }

    ArchiveNotes(listNoteByBookmarker, navController, false, selectColorBookmarker)
}