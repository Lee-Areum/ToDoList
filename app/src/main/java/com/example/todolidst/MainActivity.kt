package com.example.todolidst

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolidst.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding //viewBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewBinder setting
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar setting
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view -> //fab 버튼 클릭시
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //calender setting
        setupCalendar()

        //recyclerview setting
        setupRecyclerview()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { //메뉴 연결
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //메뉴 클릭시
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.menu_month -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupCalendar(){
        binding.contentMain.mainCalanderView.setOnDateChangeListener{ view, year, month, dayOfMonth ->
            //month는 0부터 시작, dayOfMonth : 날짜
            val dayOfWeek = MyCalendar.getInstance().getWeek(year,month,dayOfMonth) //요일
            val date = "${year}.${String.format("%02d",month+1)}.${String.format("%02d",dayOfMonth)}(${dayOfWeek})"
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
                    "item${dayToDo.subCategory} 클릭",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createDayToDoList() : ArrayList<DayToDo>{
        return arrayListOf<DayToDo>(
            DayToDo(
                "대단원1",
                "소단원1",
                false
            ),DayToDo(
                "대단원1",
                "소단원2",
                true
            ),DayToDo(
                "대단원2",
                "소단원3",
                false
            ),DayToDo(
                "대단원3",
                "소단원4",
                false
            ),DayToDo(
                "대단원3",
                "소단원4",
                false
            ),DayToDo(
                "대단원3",
                "소단원4",
                false
            ),DayToDo(
                "대단원3",
                "소단원4",
                false
            )
        )
    }
}