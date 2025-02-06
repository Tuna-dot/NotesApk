package com.example.notesapk.presenter.notedetail

import com.example.notesapk.model.data.models.NoteModel

interface DetailNoteContract {
    interface View {
        fun showNote(note: NoteModel)
        fun showError(message: String)
    }
    interface Presenter {
        fun saveNote(note: NoteModel)
        fun updateNote(note: NoteModel)
        fun getNoteById(noteId: Int): NoteModel?
    }
}