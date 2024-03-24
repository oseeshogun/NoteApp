package com.example.notesapp.features.notes.domain.usecases

import com.example.notesapp.features.notes.domain.models.InvalidNoteException
import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.repositories.NoteRepository

class AddNoteUseCase (
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title of the note can not be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content of the note can not be empty")
        }
        repository.insert(note)
    }
}