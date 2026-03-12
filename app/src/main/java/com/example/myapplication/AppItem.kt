package com.example.myapplication

import androidx.annotation.DrawableRes

data class AppItem(
    val id: String,
    @DrawableRes val icon: Int,
    val title: String,
    val description: String,
    val category: String,
    val screenshotUrls: List<String> = emptyList()
)