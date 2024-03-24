package com.example.notesapp.features.notes.domain.models

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.ui.theme.BabyBlue
import com.example.notesapp.ui.theme.LightGreen
import com.example.notesapp.ui.theme.RedOrange
import com.example.notesapp.ui.theme.RedPink
import com.example.notesapp.ui.theme.Violet

@Entity(tableName = "notes")
data class Note (
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val notColors = listOf<Color>(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception()