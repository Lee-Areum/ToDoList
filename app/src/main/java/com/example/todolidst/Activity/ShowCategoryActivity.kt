package com.example.todolidst.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.todolidst.DB.DAO.Category
import com.example.todolidst.DB.DBHelper
import com.example.todolidst.DB.DBHelperCategory
import com.example.todolidst.R
import com.example.todolidst.databinding.ActivityShowCategoryBinding
import com.example.todolidst.recyclerview.CategoryAdapter
import kotlin.math.log

class ShowCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowCategoryBinding //viewBinder
    lateinit var adapter: CategoryAdapter
    val TAG = "reum/ShowCategory"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabPlus.setOnClickListener { //기본 FAB 클릭시
            showDialog()
        }

        setRecyclerview()
    }

    fun setRecyclerview() {
        val db = DBHelperCategory(this, null)
        adapter = CategoryAdapter.getInstance(db)
        adapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: Category, pos: Int) {
                Log.v(TAG,"onclick")
                Toast.makeText(this@ShowCategoryActivity, "${pos}가 클릭됨", Toast.LENGTH_SHORT).show()
            }
        })
        binding.categoryRecyclerview.adapter = adapter
    }

    fun showDialog() {
        val et = EditText(this)
        et.gravity = Gravity.CENTER
        val builder = AlertDialog.Builder(this)
            .setTitle("카테고리")
            .setView(et)
            .setPositiveButton(R.string.dialog_btnOK,
                DialogInterface.OnClickListener { dialog, id ->
                    var content :String = et.text.toString()
                    var flag = adapter.insertCategory(Category(
                        -1,
                        content,
                    ))
                    if(flag >= 0){
                        Log.v(TAG,"flag : ${flag}" )
                        adapter.notifyDataSetChanged()
                    }else{
                        Toast.makeText(this@ShowCategoryActivity, "데이터 입력에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                })
            .setNegativeButton(R.string.dialog_btnCancel,
                DialogInterface.OnClickListener { dialog, i ->
                    dialog.dismiss()
                })
        builder.show()
    }
}