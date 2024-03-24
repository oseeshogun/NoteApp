package com.example.notesapp.features.notes.domain.repositories

import com.example.notesapp.features.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun streamAll(): Flow<List<Note>>

    suspend fun getById(id: Int): Note?

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)
}