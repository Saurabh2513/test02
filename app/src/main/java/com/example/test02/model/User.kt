package com.example.test02.model

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val birthDate: String,
    val gender: String
)
