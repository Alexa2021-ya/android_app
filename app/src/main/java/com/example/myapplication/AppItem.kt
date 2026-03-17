package com.example.myapplication

data class AppItem(
    val id: String,
    val icon: String,
    val title: String,
    val description: String,
    val category: String,
    val screenshotUrls: List<String> = emptyList()
)