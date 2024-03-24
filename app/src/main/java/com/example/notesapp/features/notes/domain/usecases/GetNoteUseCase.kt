package com.example.notesapp.features.notes.domain.usecases

import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.repositories.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getById(id)
    }
}