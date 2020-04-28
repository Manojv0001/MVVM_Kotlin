package com.nullbyte.mvvm.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nullbyte.mvvm.repository.NoteRepository

class ViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(NoteRepository(application)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}