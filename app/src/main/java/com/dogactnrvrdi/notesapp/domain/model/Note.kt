package com.dogactnrvrdi.notesapp.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogactnrvrdi.notesapp.R

@Entity(tableName = "notes")
data class Note(
    val title: String,
    val content: String,
    val created: Long = System.currentTimeMillis(),
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
) {
    companion object {
        val noteColors = listOf(Color(0xFFEB9AFF), Color.Blue, Color.Green, Color.Yellow, Color.Cyan)
    }
}

class InvalidNoteException(message: String) : Exception(message)