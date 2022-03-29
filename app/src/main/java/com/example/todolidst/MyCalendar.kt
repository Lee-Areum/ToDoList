package com.example.todolidst

import java.util.*

class MyCalendar private constructor(){
    val week = arrayOf("일","월","화","수","목","금","토")
    var cal = Calendar.getInstance()

    companion object { //동반 객체(static 대신)
        @Volatile private var instance: MyCalendar? = null; //singleton 패턴 사용

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
        return week[num];
    }
}