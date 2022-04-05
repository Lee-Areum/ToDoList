package com.example.todolidst

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolidst.databinding.ActivityEditMainplanBinding
import java.util.*
import kotlin.collections.ArrayList

class EditMainPlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditMainplanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinder setting
        binding = ActivityEditMainplanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabPlus.setOnClickListener {
            Toast.makeText(this@EditMainPlanActivity, "Main FAB 클릭", Toast.LENGTH_SHORT).show()
        }

        //recyclerview setting
        setupRecyclerview()

        binding.editMainDateBtn.setOnClickListener{
            val cal = Calendar.getInstance()
            DatePickerDialog(this@EditMainPlanActivity,{datePicker, year, month, date ->
                binding.editMainDateBtn.text = "${String.format("%04d",year)}.${String.format("%02d",month+1)}.${String.format("%02d",date)}"
                binding.editMainDDayTxt.text = MyCalendar.getInstance().getDday(year,month,date)
                Toast.makeText(this@EditMainPlanActivity,"$year.${month+1}.$date",Toast.LENGTH_SHORT).show()
            }, cal.get(Calendar.YEAR),cal.get(Calendar.MARCH),cal.get(Calendar.DATE)).show()
        }
    }


    private fun setupRecyclerview(){
        binding.editMainPlanRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomAdapter(createDayToDoList()){dayToDo, position ->
                Toast.makeText(
                    this@EditMainPlanActivity,
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
                arrayListOf("","소단원2","10")
            ),DayToDo(
                1,
                true,
                arrayListOf("","소단원2","10")
            ),DayToDo(
                2,
                false,
                arrayListOf("","소단원3","5")
            ),DayToDo(
                3,
                false,
                arrayListOf("","소단원4","5")
            ),DayToDo(
                4,
                true,
                arrayListOf("","소단원1","50")
            ),DayToDo(
                5,
                false,
                arrayListOf("","소단원1","5")
            ),DayToDo(
                6,
                false,
                arrayListOf("","소단원1","5")
            )
        )
    }
}