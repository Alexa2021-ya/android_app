package com.example.myapplication.data.appdetails

import androidx.room.TypeConverter
import com.example.myapplication.domain.appdetails.Category

class CategoryConverter {
    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    @TypeConverter
    fun toCategory(categoryName: String): Category = Category.valueOf(categoryName)
}