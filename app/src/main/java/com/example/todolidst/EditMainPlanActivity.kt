package com.example.todolidst

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolidst.databinding.ActivityEditMainplanBinding
import java.util.*
import kotlin.collections.ArrayList

class EditMainPlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditMainplanBinding
    lateinit var adapter: CustomAdapter

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
            DatePickerDialog(this@EditMainPlanActivity,{datePicker, year, month, date -> //datepickerdialog 띄우기
                binding.editMainDateBtn.text = "${String.format("%04d",year)}.${String.format("%02d",month+1)}.${String.format("%02d",date)}" //버튼 text 변경
                binding.editMainDDayTxt.text = MyCalendar.getInstance().getDday(year,month,date) //d-day 설정
                Toast.makeText(this@EditMainPlanActivity,"$year.${month+1}.$date",Toast.LENGTH_SHORT).show()
            }, cal.get(Calendar.YEAR),cal.get(Calendar.MARCH),cal.get(Calendar.DATE)).show()
        }
    }


    private fun setupRecyclerview(){
        adapter = CustomAdapter(createDayToDoList())
        Log.v("areum","${adapter.itemCount}개 있음")
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: DayToDo, pos: Int) {
                //TODO : 클릭하면 check 되도록
                val check : CheckBox = v.findViewById(R.id.main_item_check)
                check.setChecked(!check.isChecked)
            }
        })
        binding.editMainPlanRecyclerview.adapter = adapter
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