package com.nullbyte.mvvm.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nullbyte.mvvm.R
import com.nullbyte.mvvm.adapter.NoteAdapter
import com.nullbyte.mvvm.constant.Constants
import com.nullbyte.mvvm.db.entity.Note
import com.nullbyte.mvvm.viewModel.NoteViewModel
import com.nullbyte.mvvm.viewModel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var modelFactory: ViewModelFactory
    private var notes: List<Note> = ArrayList()
    private var recyclerAdapter: NoteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        modelFactory = ViewModelFactory(application)
        noteViewModel = ViewModelProviders.of(this, modelFactory).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this,
            Observer {
                notes = it
                rv_notes.adapter = NoteAdapter(notes)
//                recyclerAdapter!!.notifyDataSetChanged()
//                adapter.setNotes(it)
            }
        )
        Log.i("mSize", notes.size.toString())
        recyclerAdapter = NoteAdapter(notes)

        rv_notes.layoutManager = LinearLayoutManager(this)
        rv_notes.adapter = recyclerAdapter

        fab_add_note.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_all_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            fab_add_note -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivityForResult(intent, Constants.ADD_NOTE_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val newNote = Note(
                data?.getStringExtra(Constants.NOTE_TITLE)!!,
                data.getStringExtra(Constants.NOTE_DESCRIPTION)!!
            )
            noteViewModel.insert(newNote)
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
        }

    }
}
