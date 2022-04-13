package com.example.todolidst

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todolidst.databinding.ActivityShowMainplanBinding
import java.util.*
import kotlin.collections.ArrayList

class ShowMainPlanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShowMainplanBinding //viewBinder
    lateinit var adapter: CustomAdapter
    val TAG = "areum_showMain"

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
        adapter = CustomAdapter(createDayToDoList())
        Log.v("areum","${adapter.itemCount}개 있음")
        val swipeHelperCallBack = SwipeHelperCallback(adapter).apply{
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 7 * 2) //1000 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallBack).attachToRecyclerView(binding.showMainplanRecyclerview)
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: DayToDo, pos: Int) {
                swipeHelperCallBack.removePreviousClamp(binding.showMainplanRecyclerview)
                val check : CheckBox = v.findViewById(R.id.main_item_check)
                check.setChecked(!check.isChecked)
            }
        })
        adapter.setOnItemEditClickListener(object : CustomAdapter.OnItemEditClickListener{
            override fun onItemEditClick(v: View, data: DayToDo, pos: Int) {
                if (!swipeHelperCallBack.isSlided()) {
                    Log.v(TAG,"swipe 되지 않음")
                    return
                }
                val intent = Intent(this@ShowMainPlanActivity, EditMainPlanActivity::class.java).apply {  }
                startActivity(intent)
            }
        })
        adapter.setOnItemDelClickListener(object : CustomAdapter.OnItemDelClickListener{
            override fun onItemDelClick(v: View, data: DayToDo, pos: Int) {
                if (!swipeHelperCallBack.isSlided()) {
                    Log.v(TAG,"swipe 되지 않음")
                    return
                }
                Log.v(TAG,"${data.idNo} : 삭제버튼 클릭")
                Toast.makeText(this@ShowMainPlanActivity,"${data.idNo}가 삭제됨",Toast.LENGTH_SHORT).show()
                //TODO: 데이터 삭제
                adapter.notifyDataSetChanged()
            }
        })
        binding.showMainplanRecyclerview.adapter = adapter
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