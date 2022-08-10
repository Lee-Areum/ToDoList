package com.example.todolidst.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolidst.DB.DAO.Category
import com.example.todolidst.DB.DBHelperCategory
import com.example.todolidst.R

class CategoryAdapter(private var db : DBHelperCategory) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    //main(일별) recyclerivew
    private val TAG : String = "areum_category_Adapter"
    private var categoryList: ArrayList<Category> = getData()

    private var clickListener: OnItemClickListener? = null

    fun setDB(database:DBHelperCategory){
        db = database
    }

    companion object{
        private var instance : CategoryAdapter?= null
        fun getInstance(database : DBHelperCategory):CategoryAdapter {
            if (instance == null) {
                instance = CategoryAdapter(database)
            } else {
                instance!!.setDB(database)
            }
            return instance!!
        }
    }

    fun getCategoryList() : ArrayList<Category>{
        return categoryList
    }

    @SuppressLint("Range")
    private fun getData() : ArrayList<Category>{
//        Log.v(TAG,"setData")
        val cursor = db.getAllCategory()
        cursor!!.moveToFirst()
        val list : ArrayList<Category> = ArrayList()
        while(cursor.moveToNext()){
            val category = Category(
                categoryID =  cursor.getInt(cursor.getColumnIndex("categoryID")),
                category = cursor.getString(cursor.getColumnIndex("category")),
            )
            list.add(category)
//            Log.v(TAG,"${category.categoryID} : ${category.category}")
        }
        Log.v(TAG,"category size : ${list.size}")
        cursor.close()
        return list
    }

    fun insertCategory(category: Category):Int{
        Log.v(TAG,"insert")
        if(db == null){
            Log.v(TAG,"null")
            return -1;
        }
        var flag = db.insertCategory(category)
        categoryList = getData()
        return flag
    }

    interface OnItemClickListener{
        fun onItemClick(v:View,data:Category,pos:Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.clickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //recyclerview의 한 아이탬을 대변함
        val txtCategory :TextView = view.findViewById(R.id.txtCategory)
        fun bindItem(category: Category) {
            txtCategory.text = category.category
            itemView.setOnClickListener{
                clickListener?.onItemClick(itemView,category,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_category_list,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    private fun createDayToDoList() : ArrayList<Category> {
        return arrayListOf<Category>(
            Category(
                0,
                "대단원1",
            ),
            Category(
                1,
                "대단원2",
            ),
            Category(
                2,
                "대단원3",
            ),
        )
    }

}