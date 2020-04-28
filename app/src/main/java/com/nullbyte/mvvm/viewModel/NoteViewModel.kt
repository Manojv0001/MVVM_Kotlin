package com.nullbyte.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nullbyte.mvvm.db.entity.Note
import com.nullbyte.mvvm.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}