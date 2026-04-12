package com.example.myapplication.data.appdetails

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.appdetails.Category

@Entity(tableName = "app_details")
data class AppDetailsEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val developer: String,
    val category: Category,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshots: String? = null,
    val description: String,
    val lastUpdated: Long = System.currentTimeMillis()
)