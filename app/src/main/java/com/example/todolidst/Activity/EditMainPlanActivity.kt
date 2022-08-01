package com.example.todolidst.Activity

import android.app.AlertDialog
import android.app.DatePickerDialog
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
import com.example.todolidst.*
import com.example.todolidst.databinding.ActivityEditMainplanBinding
import com.example.todolidst.recyclerview.CustomAdapter
import com.example.todolidst.recyclerview.SwipeHelperCallback
import java.util.*
import kotlin.collections.ArrayList

class EditMainPlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditMainplanBinding
    lateinit var adapter: CustomAdapter
    val TAG:String = "areum_editMain"

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

    private fun setupRecyclerview(){ //snaphelper 여기
        adapter = CustomAdapter(createDayToDoList())
        Log.v("areum","${adapter.itemCount}개 있음")
        val swipeHelperCallBack = SwipeHelperCallback(adapter).apply{
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 7 * 2) //1000 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallBack).attachToRecyclerView(binding.editMainPlanRecyclerview)
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: DayToDo, pos: Int) {
                swipeHelperCallBack.removePreviousClamp(binding.editMainPlanRecyclerview)
            }
        })
        adapter.setOnItemEditClickListener(object : CustomAdapter.OnItemEditClickListener {
            override fun onItemEditClick(v: View, data: DayToDo, pos: Int) {
                if (!swipeHelperCallBack.isSlided()) {
                    Log.v(TAG,"swipe 되지 않음")
                    return
                }
                val mDialogView = LayoutInflater.from(this@EditMainPlanActivity).inflate(R.layout.dialog_edit_plan,null)
                val containter: LinearLayout = mDialogView.findViewById(R.id.dialog_spinnerContainer)
                val btnOK: Button = mDialogView.findViewById(R.id.dialog_buttonOK)
                containter.visibility = View.VISIBLE
                val mBuilder = AlertDialog.Builder(this@EditMainPlanActivity)
                    .setView(mDialogView)
                    .setTitle("Sub 계획 수정")
                val alertDialog = mBuilder.show()

                btnOK.setOnClickListener{
                    //TODO: 수정된 data 입력
                    alertDialog.dismiss()
                }
            }
        })
        adapter.setOnItemDelClickListener(object : CustomAdapter.OnItemDelClickListener {
            override fun onItemDelClick(v: View, data: DayToDo, pos: Int) {
                if (!swipeHelperCallBack.isSlided()) {
                    Log.v(TAG,"swipe 되지 않음")
                    return
                }
                Log.v(TAG,"${data.idNo} : 삭제버튼 클릭")
                Toast.makeText(this@EditMainPlanActivity,"${data.idNo}가 삭제됨",Toast.LENGTH_SHORT).show()
                //TODO: 데이터 삭제
                adapter.notifyDataSetChanged()
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
            ), DayToDo(
                1,
                true,
                arrayListOf("","소단원2","10")
            ), DayToDo(
                2,
                false,
                arrayListOf("","소단원3","5")
            ), DayToDo(
                3,
                false,
                arrayListOf("","소단원4","5")
            ), DayToDo(
                4,
                true,
                arrayListOf("","소단원1","50")
            ), DayToDo(
                5,
                false,
                arrayListOf("","소단원1","5")
            ), DayToDo(
                6,
                false,
                arrayListOf("","소단원1","5")
            )
        )
    }
}