package com.example.notesapp.features.notes.presentation.utils

sealed class Screen(val route: String) {
    object NoteScreen: Screen("notes_screen")
    object EditNoteScreen: Screen("edit_note_screen")
}
