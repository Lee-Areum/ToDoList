package com.example.todolidst.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolidst.DB.DAO.Category
import com.example.todolidst.DB.DAO.Plan

class DBHelper(context: Context, factory:SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context,DATABASE_NAME,factory,
    DATABASE_VERSION){
    private val TABLE_NAME: String = "plan"

    override fun onCreate(db: SQLiteDatabase) {
        var query = ("CREATE TABLE ${TABLE_NAME} ("+
            "${ID} INT NOT NULL PRIMARY KEY,"+
            "${DATE} DATE NULL,"+
            "${CATEGORY_ID} INT NULL,"+
            "${CATEGORY} VARCHAR(50) NULL,"+
            "${CONTENT} VARCHAR(100) NULL,"+
            "${iS_DONE} INT NULL DEFAULT 0 )")

        db.execSQL(query)

        query = ("CREATE TABLE category ("+
                "${CATEGORY_ID} INT NOT NULL PRIMARY KEY,"+
                "${CATEGORY} DATE NULL)"
                )
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
        db.execSQL("DROP TABLE IF EXISTS category")
        onCreate(db)
    }

    fun insertPlan(plan : Plan) : Int{
        val values = ContentValues()

        values.put("date",plan.date)
        values.put("content",plan.content)
        values.put("categoryID",plan.categoryID)
        values.put("isDone",plan.isDone)

        val db = this.writableDatabase

        val index = db.insert(TABLE_NAME, null, values)
        db.close()

        return index.toInt()
    }

    fun getAllPlan() : Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null)
    }

    fun insertCategory(category : Category):Int{
        val values = ContentValues()

        values.put("categoryID",category.categoryID)
        values.put("category",category.category)

        val db = this.writableDatabase

        val index = db.insert("category", null, values)
        db.close()

        return index.toInt()
    }

    fun getAllCategory() : Cursor?{
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM category",null)
    }

    companion object{
        val DATABASE_NAME = "ToDoList"
        val DATABASE_VERSION = 1

        val ID = "id"
        val DATE = "date"
        val CATEGORY_ID = "categoryID"
        val CATEGORY = "category"
        val CONTENT = "content"
        val iS_DONE = "isDone"
    }
}