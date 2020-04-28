package com.nullbyte.mvvm.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nullbyte.mvvm.R
import com.nullbyte.mvvm.constant.Constants
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        if (et_title.text.toString().trim().isBlank() || et_description.text.toString().trim()
                .isBlank()
        ) {
            Toast.makeText(this, "Can not insert empty note!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        data.putExtra(Constants.NOTE_TITLE, et_title.text.toString())
        data.putExtra(Constants.NOTE_DESCRIPTION, et_description.text.toString())
        setResult(Activity.RESULT_OK, data)
        finish()
    }

}
