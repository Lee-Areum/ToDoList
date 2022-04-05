package com.example.todolidst

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolidst.databinding.ActivityShowMainplanBinding
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

        binding.btnShowMainDate.setOnClickListener{
            val cal = Calendar.getInstance()
            DatePickerDialog(this@ShowMainPlanActivity,{datePicker, year, month, date ->
                binding.btnShowMainDate.text = "${String.format("%04d",year)}.${String.format("%02d",month+1)}"
                Toast.makeText(this@ShowMainPlanActivity,"$year.$month",Toast.LENGTH_SHORT).show()
            }, cal.get(Calendar.YEAR),cal.get(Calendar.MARCH),cal.get(Calendar.DATE)).show()
        }

        //recyclerview setting
        setupRecyclerview()
    }

    private fun setupRecyclerview(){
        binding.showMainplanRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomAdapter(createDayToDoList()){dayToDo, position ->
                Toast.makeText(
                    this@ShowMainPlanActivity,
                    "item${dayToDo.strList[1]} 클릭",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createDayToDoList() : ArrayList<DayToDo>{
        return arrayListOf<DayToDo>(
            DayToDo(
                0,
                false,
                arrayListOf("2022.05.03","소단원2","5","10")
            ),DayToDo(
                1,
                true,
                arrayListOf("2022.04.21","소단원2","2","10")
            ),DayToDo(
                2,
                false,
                arrayListOf("2022.08.12","소단원3","2","5")
            ),DayToDo(
                3,
                false,
                arrayListOf("2022.12.12","소단원4","3","5")
            ),DayToDo(
                4,
                true,
                arrayListOf("","소단원1","30","50")
            ),DayToDo(
                5,
                false,
                arrayListOf("","소단원1","3","5")
            ),DayToDo(
                6,
                false,
                arrayListOf("","소단원1","3","5")
            )
        )
    }
}