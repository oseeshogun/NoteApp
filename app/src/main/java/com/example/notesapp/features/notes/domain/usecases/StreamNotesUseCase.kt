package com.example.notesapp.features.notes.domain.usecases

import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.repositories.NoteRepository
import com.example.notesapp.features.notes.domain.utils.NoteOrder
import com.example.notesapp.features.notes.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StreamNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Note>> {
        return repository.streamAll().map { notes ->
            when (noteOrder.orderType) {
                OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    }
                }

                OrderType.Descending -> {

                    when (noteOrder) {
                        is NoteOrder.Color ->  notes.sortedByDescending { it.color }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                    }
                }
            }

        }
    }
}