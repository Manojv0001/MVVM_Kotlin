package com.nullbyte.mvvm.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.nullbyte.mvvm.db.NoteDatabase
import com.nullbyte.mvvm.db.dao.NoteDao
import com.nullbyte.mvvm.db.entity.Note

class NoteRepository(application: Application): NoteDao {

    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Note>>

    init {
        val database = NoteDatabase.getInstance(application)
        noteDao = database.noteDao()
        allNotes = database.noteDao().getAllNotes()
    }

    override fun insert(note: Note) {
        Log.i("Note", note.title)
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    override fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    override fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}

class DeleteAllNotesAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
    override fun doInBackground(vararg note: Note?) {
        noteDao.deleteAllNotes()
    }
}

class InsertNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
    override fun doInBackground(vararg note: Note?) {
        noteDao.insert(note[0]!!)
        Log.i("dao", note[0].toString())
    }
}
