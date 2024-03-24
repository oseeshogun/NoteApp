package com.example.notesapp.features.notes.domain.usecases

data class NoteUseCases(
    val streamNotesUseCase: StreamNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase
)
