package com.example.notesapk.model.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapk.model.data.db.daos.NoteDao
import com.example.notesapk.model.data.models.NoteModel

@Database(entities = [NoteModel::class], version = 2, exportSchema = false)
abstract class AppDataBase: RoomDatabase()  {

    abstract fun noteDao(): NoteDao
}