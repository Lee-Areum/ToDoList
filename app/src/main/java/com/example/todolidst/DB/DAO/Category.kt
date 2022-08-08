package com.example.todolidst.DB.DAO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date


class Category(categoryID : Int, category : String){
    val categoryID = categoryID
    val category = category
}

//https://www.geeksforgeeks.org/android-sqlite-database-in-kotlin/