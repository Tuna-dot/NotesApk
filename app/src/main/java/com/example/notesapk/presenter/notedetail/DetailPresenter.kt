package com.example.notesapk.presenter.notedetail

import com.example.notesapk.App
import com.example.notesapk.model.data.models.NoteModel

class DetailPresenter(private val view: DetailNoteContract.View) : DetailNoteContract.Presenter {

    override fun saveNote(note: NoteModel) {
        App.appDataBase?.noteDao()?.insert(note)
    }

    override fun updateNote(note: NoteModel) {
        App.appDataBase?.noteDao()?.update(note)
    }

    override fun getNoteById(noteId: Int): NoteModel? {
        App.appDataBase?.noteDao()?.getById(noteId)
        return NoteModel("", "", 0, "")
    }
}