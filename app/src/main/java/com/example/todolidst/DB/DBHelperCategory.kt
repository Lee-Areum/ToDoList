package com.example.todolidst.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.todolidst.DB.DAO.Category

class DBHelperCategory (context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context,DATABASE_NAME,factory,
    DATABASE_VERSION) {
    private val TABLE_NAME: String = "category"
    val TAG = "areum/DBHelperCategory"

    override fun onCreate(db: SQLiteDatabase) {
        var query = ("CREATE TABLE ${TABLE_NAME} (" +
                "${CATEGORY_ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "${CATEGORY} DATE NULL)"
                )
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertCategory(category: Category): Int {
        val values = ContentValues()

        values.put(CATEGORY, category.category)

        val db = this.writableDatabase

        val index = db.insert(TABLE_NAME, null, values)
        db.close()

        return index.toInt()
    }

    fun getAllCategory(): Cursor? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null)
        Log.v(TAG,"cursor size : ${cursor.count}")
        return cursor
    }

    companion object {
        val DATABASE_NAME = "ToDoListCategory"
        val DATABASE_VERSION = 3

        val CATEGORY_ID = "categoryID"
        val CATEGORY = "category"
    }
}