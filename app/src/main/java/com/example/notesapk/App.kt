package com.example.notesapk

import android.app.Application
import androidx.room.Room
import com.example.notesapk.data.db.AppDataBase
import com.example.notesapk.utils.PreferenceHelper

class App : Application() {

    companion object {
        var appDataBase: AppDataBase? = null

    }

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
        getInstance()
    }

    private fun getInstance(): AppDataBase? {

        if (appDataBase == null) {
            appDataBase = applicationContext.let { context ->
                Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    "note.database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

            }
        }
        return appDataBase
    }
}