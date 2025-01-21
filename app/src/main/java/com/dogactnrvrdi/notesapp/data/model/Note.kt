package com.dogactnrvrdi.notesapp.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "notes")
data class Note(
    val title: String,
    val description: String,
    val created: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "color", defaultValue = "0xFFEDB7ED")
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
) {

    val createdDateFormatted: String get() = DateFormat.getDateTimeInstance().format(created)

    companion object {

        val noteColors =
            listOf(
                Color(0xFFEDB7ED),
                Color(0xFF82A0D8),
                Color(0xFF8DDFCB),
                Color(0xFFECEE81),
                Color(0xFF5C8984),
                Color(0xFFFF9B9B)
            )

        fun getRandomColor(): Int {
            return noteColors.random().toArgb()
        }
    }
}