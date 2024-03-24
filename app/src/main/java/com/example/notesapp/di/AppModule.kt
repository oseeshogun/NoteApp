package com.example.notesapp.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.features.notes.data.local.NoteDatabase
import com.example.notesapp.features.notes.data.local.repositories.NoteRepositoryImpl
import com.example.notesapp.features.notes.domain.repositories.NoteRepository
import com.example.notesapp.features.notes.domain.usecases.AddNoteUseCase
import com.example.notesapp.features.notes.domain.usecases.DeleteNoteUseCase
import com.example.notesapp.features.notes.domain.usecases.GetNoteUseCase
import com.example.notesapp.features.notes.domain.usecases.NoteUseCases
import com.example.notesapp.features.notes.domain.usecases.StreamNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            streamNotesUseCase = StreamNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository)
        )
    }
}