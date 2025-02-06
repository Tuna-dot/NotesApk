package com.example.notesapk.presenter.notes

import com.example.notesapk.App
import com.example.notesapk.model.data.models.NoteModel

class NotePresenter(private val view: NoteContract.View) : NoteContract.Presenter {
    override fun loadNotes() {
    App.appDataBase?.noteDao()?.getAll()?.observeForever { notes ->
        view.showNotes(notes)
         }
    }

    override fun deleteNote(note: NoteModel) {
        App.appDataBase?.noteDao()?.delete(note)
        loadNotes()
    }

}