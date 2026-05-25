package com.example.notesofdrowned.screens.notesminimal

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.notesofdrowned.ListNoteObjects
import com.example.notesofdrowned.LocalListNote
import com.example.notesofdrowned.database.writedbarchmini.WorkDBArchMini
import com.example.notesofdrowned.screens.archivenotes.ArchiveNotes
import com.example.notesofdrowned.screens.componentsNOD.ComponentsNOD


@Composable
fun NotesMinimal(navController: NavHostController, workDBArchMini: WorkDBArchMini, isLoadListObjNotes: Boolean) {
    val listNote = LocalListNote.current

    if(isLoadListObjNotes){
        Log.d("dbMu", "updater(LibraryNotesMinimal)")
        listNote.clear()

        workDBArchMini.getAllNodeWithColor().forEach{ noteArchMini->
            listNote.add(ListNoteObjects.BoxNote(
                idNote = noteArchMini.id,
                titleNote = noteArchMini.title,
                textNote = noteArchMini.description,
                colorBookmarker = if(noteArchMini.colorBookmarker!="") noteArchMini.colorBookmarker else ComponentsNOD.defaultColorBookmarker
            ))
        }
    }

    ArchiveNotes(listNote, navController, true, ComponentsNOD.defaultColorBookmarker)
}
