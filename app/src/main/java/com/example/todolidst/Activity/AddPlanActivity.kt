package com.example.todolidst.Activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolidst.DB.DAO.Category
import com.example.todolidst.DB.DAO.Plan
import com.example.todolidst.DB.DBHelper
import com.example.todolidst.DB.DBHelperCategory
import com.example.todolidst.MyCalendar
import com.example.todolidst.R
import com.example.todolidst.databinding.ActivityAddPlanBinding
import com.example.todolidst.recyclerview.CategoryAdapter
import com.example.todolidst.recyclerview.CustomAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AddPlanActivity  : AppCompatActivity(){
    private lateinit var binding: ActivityAddPlanBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var planAdapter : CustomAdapter
    private lateinit var categoryList: ArrayList<Category>
    val cal = Calendar.getInstance()
    var TAG = "areum_addPlanActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListener()
    }

    private fun setListener(){
        val db = DBHelperCategory(this, null)
        //spinner
        categoryAdapter = CategoryAdapter.getInstance(db)
        categoryList = categoryAdapter.getCategoryList()
        var datas:ArrayList<String> = ArrayList()
        for(c in categoryList){
            datas.add(c.category)
        }

        var categoryAdapter : ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.item_spinner,datas)
        binding.dialogSpinner.setAdapter(categoryAdapter)
        Log.v(TAG,"size : ${categoryAdapter.count}")

        //button
        val dateFormat = SimpleDateFormat("yyyy.MM.dd")
        binding.dialogSubDate.setText(dateFormat.format(cal.time))
        binding.dialogSubDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val dialog = DatePickerDialog(this@AddPlanActivity,{datePicker, year, month, date -> //datepickerdialog 띄우기
                var test = cal.set(year,month,date) //TODO : 다시 보기
                binding.dialogSubDate.setText(dateFormat.format(cal.time))
                binding.dialogMainDDayTxt.text = MyCalendar.getInstance().getDday(year,month,date) //d-day 설정
                Toast.makeText(this@AddPlanActivity,"${cal.time}", Toast.LENGTH_SHORT).show()
            }, cal.get(Calendar.YEAR),cal.get(Calendar.MARCH),cal.get(Calendar.DATE))
            dialog.datePicker.minDate = System.currentTimeMillis() - 1000
            dialog.show()
        }

        var planDB = DBHelper(this,null)
        planAdapter = CustomAdapter.getInstance(planDB)
        binding.dialogButtonOK.setOnClickListener{
            if(binding.txtContent.text.length <= 0){
                Toast.makeText(this@AddPlanActivity, "계획을 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{
                //
//                val l  = LocalDate.parse(binding.dialogSubDate.text.toString(),DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                var p = Plan(
                        id = -1,
                        date = dateFormat.parse(dateFormat.format(cal.time)).time,
                        categoryID = categoryList.get(binding.dialogSpinner.selectedItemPosition).categoryID,
                        category = categoryList.get(binding.dialogSpinner.selectedItemPosition).category,
                        content = binding.txtContent.text.toString(),
                        isDone = 0
                    )
                val flag = planAdapter.insertPlan(p)
                Log.v(TAG,"date : ${p.date} categoryID : ${p.categoryID} category : ${p.category} content : ${p.content}")
                Log.v(TAG,"insert : ${flag}")
                if(flag >= 0){
                    planAdapter.notifyDataSetChanged()
                    finish()
                }else{
                    Toast.makeText(this,"데이터입력에 실패하였습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
