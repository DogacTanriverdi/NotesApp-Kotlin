package com.dogactnrvrdi.notesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var description: String,
    var created: Long = System.currentTimeMillis()
) : Parcelable {
    val createdDateFormatted: String get() = DateFormat.getDateTimeInstance().format(created)
}
