package com.nullbyte.mvvm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nullbyte.mvvm.db.entity.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Query("DELETE from notes_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>
}