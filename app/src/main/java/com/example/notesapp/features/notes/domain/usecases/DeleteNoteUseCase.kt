package com.example.notesapp.features.notes.domain.usecases

import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.repositories.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.delete(note)
    }
}