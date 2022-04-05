package com.example.todolidst

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolidst.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding //viewBinder
    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewBinder setting
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar setting
        setSupportActionBar(binding.toolbar)

        binding.fabPlus.setOnClickListener {
            toggleFab()
        }

        binding.fabMain.setOnClickListener {
            Toast.makeText(this@MainActivity,"Main FAB 클릭",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, ShowMainPlanActivity::class.java).apply {  }
            startActivity(intent)
        }

        binding.fabSub.setOnClickListener {
            Toast.makeText(this@MainActivity,"Sub FAB 클릭",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, EditMainPlanActivity::class.java).apply {  }
            startActivity(intent)
        }

        //calender setting
        setupCalendar()

        //recyclerview setting
        setupRecyclerview()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { //메뉴 연결, 메뉴 생성과 초기화
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //메뉴 클릭시
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val toast = Toast.makeText(this@MainActivity,"",Toast.LENGTH_SHORT)
        when(item.itemId){
            R.id.menu_month -> toast.setText("월별")
            R.id.menu_week -> toast.setText("주별")
            R.id.menu_date -> toast.setText("이미 일별 화면입니다.")
        }
        toast.show()
        return super.onOptionsItemSelected(item)
    }

    //FAB 버튼 클릭시 동작하는 애니메이션
    private fun toggleFab(){
        Toast.makeText(this@MainActivity,"add FAB 클릭 ${isFabOpen}",Toast.LENGTH_SHORT).show()

        //플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션 세팅
        if(isFabOpen){
            ObjectAnimator.ofFloat(binding.fabMain, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabSub, "translationY",0f).apply{start()}
            ObjectAnimator.ofFloat(binding.fabPlus, View.ROTATION,45f,0f).apply{start()}
        }else{ //플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션 세팅
            ObjectAnimator.ofFloat(binding.fabMain, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabSub, "translationY",-360f).apply{start()}
            ObjectAnimator.ofFloat(binding.fabPlus, View.ROTATION,0f,45f).apply{start()}
        }
        isFabOpen = !isFabOpen
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
        binding.contentMain.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomAdapter(createDayToDoList()){dayToDo, position ->
                Toast.makeText(
                    this@MainActivity,
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
                arrayListOf("대단원1","소단원1", "1")
            ),DayToDo(
                1,
                true,
                arrayListOf("대단원1","소단원2", "2")
            ),
            DayToDo(
                2,
                true,
                arrayListOf("대단원1","소단원3", "3")
            ),DayToDo(
                3,
                false,
                arrayListOf("대단원1","소단원4", "2")
            ),DayToDo(
                4,
                false,
                arrayListOf("대단원2","소단원1", "10")
            ),DayToDo(
                5,
                false,
                arrayListOf("대단원2","소단원2", "10")
            ),DayToDo(
                6,
                true,
                arrayListOf("대단원2","소단원3", "15")
            ),DayToDo(
                7,
                false,
                arrayListOf("대단원2","소단원4", "20")
            )
        )
    }
}