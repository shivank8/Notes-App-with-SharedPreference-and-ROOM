package com.shivank.notesapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")

class Notes (
    @ColumnInfo(name = "title") val noteTitle :String,
    @ColumnInfo(name = "description") val noteDescription :String
    ) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}