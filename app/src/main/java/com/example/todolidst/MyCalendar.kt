package com.example.todolidst

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyCalendar private constructor(){
    val week = arrayOf("일","월","화","수","목","금","토")
    var cal = Calendar.getInstance()

    companion object { //동반 객체(static 대신)
        @Volatile private var instance: MyCalendar? = null //singleton 패턴 사용

        @JvmStatic fun getInstance(): MyCalendar =
            instance ?: synchronized(this){ //instance가 null일 경우
                instance ?: MyCalendar().also {
                    instance = it
                }
            }
    }
    fun getWeek(year:Int,month:Int,date:Int):String{ //요일 구하는 함수
        cal.set(year,month,date)
        val num = cal.get(Calendar.DAY_OF_WEEK)-1
        return week[num]
    }

    fun getDday(year:Int,month:Int,date:Int):String{ //d-day 구하는 함수
        cal = Calendar.getInstance() //오늘 날짜
        val today = cal.time.time
        cal.set(year,month,date) //원하는 날짜로 세팅
        val dday = (cal.time.time - today) / (60*60*24*1000)
        if(dday.compareTo(0) == 0) return "Day" //원하는 날짜 == 오늘 : D-day
        return dday.toString()
    }

    fun getDday(time : Long) : String{
        val fewDay = time - System.currentTimeMillis()
        val dday = fewDay / (60*60*24*1000) + 1
        if(dday.compareTo(0) == 0) return "Day" //원하는 날짜 == 오늘 : D-day
        return dday.toString()
    }

    fun stringDateToInt(date : String):ArrayList<Int>{ //yyyy.mm.dd -> {yyyy,mm,dd}
        Log.v("areum","string이 뭔데 $date")
        val list = ArrayList<Int>()
        val str_arr = date.split(".") //'.'으로 나눔
        for(i in 0 .. str_arr.size-1 step(1)){
            list.add(str_arr.get(i).toInt())
        }
        return list
    }
}