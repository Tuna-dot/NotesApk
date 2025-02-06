package com.example.notesapk.model.data.models

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "noteModel")
data class NoteModel(
    val title: String,
    val description: String,
    val color: Int = Color.BLACK,
    val date: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
