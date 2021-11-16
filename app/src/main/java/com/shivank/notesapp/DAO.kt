package com.shivank.notesapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note :Notes)
    @Delete
    suspend fun delete(note: Notes)
    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Notes>>

    @Update
    suspend fun update(note: Notes)
}