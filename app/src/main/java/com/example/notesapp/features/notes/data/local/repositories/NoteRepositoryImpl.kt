package com.example.notesapp.features.notes.data.local.repositories

import com.example.notesapp.features.notes.data.local.data_sources.NoteDao
import com.example.notesapp.features.notes.domain.models.Note
import com.example.notesapp.features.notes.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {
    override fun streamAll(): Flow<List<Note>> {
        return dao.streamAll()
    }

    override suspend fun getById(id: Int): Note? {
        return dao.getById(id)
    }

    override suspend fun insert(note: Note) {
        return dao.insert(note)
    }

    override suspend fun delete(note: Note) {
        return dao.delete(note)
    }
}