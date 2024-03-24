package com.example.notesapp.features.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.features.notes.data.local.data_sources.NoteDao
import com.example.notesapp.features.notes.domain.models.Note

@Database(
    entities = [Note::class],
    version = 1,
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}