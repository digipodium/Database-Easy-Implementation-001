package com.example.database_easyimplementation001.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val firstName: String,
    val lastName: String,
    val number: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
