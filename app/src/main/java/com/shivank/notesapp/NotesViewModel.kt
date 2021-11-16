package com.shivank.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModal (application: Application) :AndroidViewModel(application) {
    val allNotes : LiveData<List<Notes>>
    private val repository : NotesRepo
    init {
        val dao = NotesDatabase.getDatabase(application).getNotesDao()
        repository = NotesRepo(dao)
        allNotes = repository.allNotes
    }
    fun deleteNote (note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
    fun updateNote(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
    fun addNote(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}