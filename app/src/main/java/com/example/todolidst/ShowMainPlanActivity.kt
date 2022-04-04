package com.example.todolidst

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolidst.databinding.ActivityShowMainplanBinding
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList

class ShowMainPlanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShowMainplanBinding //viewBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinder setting
        binding = ActivityShowMainplanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabPlus.setOnClickListener {
            Toast.makeText(this@ShowMainPlanActivity,"Main FAB 클릭", Toast.LENGTH_SHORT).show()
        }

        //recyclerview setting
        setupRecyclerview()
    }

    fun showDatePickerDialog(view : View) {
        //todo : https://www.android--code.com/2018/02/android-kotlin-datepickerdialog-example.html
        val cal = Calendar.getInstance()
        DatePickerDialog(this@ShowMainPlanActivity,{datePicker, year, month, date ->
            Toast.makeText(this@ShowMainPlanActivity,"$year.$month.$date",Toast.LENGTH_SHORT).show()
        }, cal.get(Calendar.YEAR),cal.get(Calendar.MARCH),cal.get(Calendar.DATE)).show()
    }

    private fun setupRecyclerview(){
        binding.showMainplanRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomAdapter(createDayToDoList()){dayToDo, position ->
                Toast.makeText(
                    this@ShowMainPlanActivity,
                    "item${dayToDo.subCategory} 클릭",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createDayToDoList() : ArrayList<DayToDo>{
        return arrayListOf<DayToDo>(
            DayToDo(
                "D-1",
                "main1",
                "1 / 5",
                false
            ),DayToDo(
                "D-1",
                "main2",
                "2 / 5",
                true
            ),DayToDo(
                "D-1",
                "main3",
                "3 / 10",
                false
            ),DayToDo(
                "D-1",
                "main4",
                "4 / 5",
                false
            ),DayToDo(
                "main5",
                "5 / 5",
                false
            ),DayToDo(
                "main6",
                "3 / 5",
                false
            ),DayToDo(
                "main7",
                "1 / 5",
                false
            )
        )
    }
}