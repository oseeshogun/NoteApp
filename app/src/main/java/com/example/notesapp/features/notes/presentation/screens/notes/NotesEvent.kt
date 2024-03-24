package com.example.notesapp.features.notes.presentation.screens.notes

import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.utils.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
