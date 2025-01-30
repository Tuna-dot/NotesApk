package com.example.notesapk.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapk.data.db.daos.NoteDao
import com.example.notesapk.data.models.NoteModel

@Database(entities = [NoteModel::class], version = 2, exportSchema = false)
abstract class AppDataBase: RoomDatabase()  {

    abstract fun noteDao(): NoteDao
}