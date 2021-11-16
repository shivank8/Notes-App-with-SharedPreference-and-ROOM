package com.shivank.notesapp

import androidx.lifecycle.LiveData

class NotesRepo(private val dao: DAO) {
    val allNotes: LiveData<List<Notes>> = dao.getAllNotes()
    suspend fun insert(note: Notes) {
        dao.insert(note)
    }
    suspend fun delete(note: Notes){
        dao.delete(note)
    }
    suspend fun update(note: Notes){
        dao.update(note)
    }
}