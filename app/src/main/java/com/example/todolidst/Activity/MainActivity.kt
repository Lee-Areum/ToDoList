package com.example.todolidst.Activity

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todolidst.*
import com.example.todolidst.databinding.ActivityMainBinding
import com.example.todolidst.recyclerview.CustomAdapter
import com.example.todolidst.recyclerview.SwipeHelperCallback

//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding //viewBinder
    lateinit var adapter: CustomAdapter
    private val TAG:String = "areum_main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //다크모드 무시
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //viewBinder setting
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar setting
        setSupportActionBar(binding.toolbar)

        binding.fabPlus.setOnClickListener { //기본 FAB 클릭시

        }

        //calender setting
        setupCalendar()

        //recyclerview setting
        setupRecyclerview()
    }

    private fun setupCalendar(){
        binding.contentMain.mainCalanderView.setOnDateChangeListener{ view, year, month, dayOfMonth ->
            //month는 0부터 시작, dayOfMonth : 날짜
            val dayOfWeek = MyCalendar.getInstance().getWeek(year,month,dayOfMonth) //요일
            val date = "$year.${String.format("%02d",month+1)}.${String.format("%02d",dayOfMonth)}($dayOfWeek)"
            val msg = "${date}가 클릭됨"
            Toast.makeText(this@MainActivity,msg,Toast.LENGTH_SHORT).show()
            binding.contentMain.mainTxtDate.setText(date)
        }
        //TODO: 오늘로 setting 필요할듯
    }

    private fun setupRecyclerview(){
        adapter = CustomAdapter(createDayToDoList())
        Log.v("areum","${adapter.itemCount}개 있음")
        val swipeHelperCallBack = SwipeHelperCallback(adapter).apply{
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 7 * 2) //1000 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallBack).attachToRecyclerView(binding.contentMain.mainRecyclerView)
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: DayToDo, pos: Int) {
            }
        })
        adapter.setOnItemEditClickListener(object : CustomAdapter.OnItemEditClickListener {
            override fun onItemEditClick(v: View, data: DayToDo, pos: Int) {
                if (!swipeHelperCallBack.isSlided()) {
                    Log.v(TAG,"swipe 되지 않음")
                    return
                }
                val mDialogView = LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog_edit_plan,null)
                val containter:LinearLayout = mDialogView.findViewById(R.id.dialog_spinnerContainer)
                val btnOK: Button = mDialogView.findViewById(R.id.dialog_buttonOK)
                containter.visibility = View.VISIBLE
                val mBuilder = AlertDialog.Builder(this@MainActivity)
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
                Toast.makeText(this@MainActivity,"${data.idNo}가 삭제됨",Toast.LENGTH_SHORT).show()
                //TODO: 데이터 삭제
                adapter.notifyDataSetChanged()
            }
        })
        binding.contentMain.mainRecyclerView.adapter = adapter
    }

    private fun createDayToDoList() : ArrayList<DayToDo>{
        return arrayListOf<DayToDo>(
            DayToDo(
                0,
                false,
                arrayListOf("대단원1","소단원1", "1")
            ), DayToDo(
                1,
                true,
                arrayListOf("대단원1","소단원2", "2")
            ),
            DayToDo(
                2,
                true,
                arrayListOf("대단원1","소단원3", "3")
            ), DayToDo(
                3,
                false,
                arrayListOf("대단원1","소단원4", "2")
            ), DayToDo(
                4,
                false,
                arrayListOf("대단원2","소단원1", "10")
            ), DayToDo(
                5,
                false,
                arrayListOf("대단원2","소단원2", "10")
            ), DayToDo(
                6,
                true,
                arrayListOf("대단원2","소단원3", "15")
            ), DayToDo(
                7,
                false,
                arrayListOf("대단원2","소단원4", "20")
            )
        )
    }
}