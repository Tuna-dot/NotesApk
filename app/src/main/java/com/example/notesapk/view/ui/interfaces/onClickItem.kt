package com.example.notesapk.view.ui.interfaces

import com.example.notesapk.model.data.models.NoteModel

interface onClickItem {

    fun onLongClick(noteModel: NoteModel)

    fun onClick(noteModel: NoteModel)
}