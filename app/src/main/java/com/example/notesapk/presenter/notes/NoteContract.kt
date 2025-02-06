package com.example.notesapk.presenter.notes

import com.example.notesapk.model.data.models.NoteModel

interface NoteContract {
    interface View {
        fun showNotes(notes: List<NoteModel>)
        fun showErorr(message: String)
    }
    interface Presenter {
        fun loadNotes()
        fun deleteNote(note: NoteModel)
    }
}