package com.example.notesapp.features.notes.presentation.screens.notes

import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.utils.NoteOrder
import com.example.notesapp.features.notes.domain.utils.OrderType

data class NotesState (
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)