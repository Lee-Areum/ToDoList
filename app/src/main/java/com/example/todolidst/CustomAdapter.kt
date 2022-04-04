package com.example.todolidst

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolidst.databinding.MainListItemBinding

class DayToDo(var subCategory: String, var isDone:Boolean){
    var mainCategory: String = ""
    var rightTxt : String = ""
    constructor( subCategory: String, rightTxt : String ,isDone:Boolean) : this(subCategory,isDone) {
        this.rightTxt = rightTxt
    }
    constructor(mainCategory: String, subCategory: String, rightTxt : String ,isDone:Boolean) : this(subCategory,isDone) {
        this.mainCategory = mainCategory
        this.rightTxt = rightTxt
    }
}

class CustomAdapter (//main(일별) recyclerivew
    private val dayToDoList: ArrayList<DayToDo>,
    private val listener: (DayToDo,Int)->Unit //클릭이벤트 처리
) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //recyclerview 생성시 호출
        val v = MainListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //특정 position의 데이터를 bind하기 위해 호출
        holder.bindItem(dayToDoList[position])
        holder.itemView.setOnClickListener{
            listener(dayToDoList[position],position)
        }
    }

    override fun getItemCount(): Int { //recyclerview 아이탬 개수를 얻기위해 호출
        return dayToDoList.size
    }

    class ViewHolder(var listItemBinding: MainListItemBinding) : //recyclerview의 한 아이탬을 대변함
            RecyclerView.ViewHolder(listItemBinding.root){
                fun bindItem(todo : DayToDo){
                    listItemBinding.mainItemCheck.setChecked(todo.isDone)
                    listItemBinding.mainItemTxtMainCategory.setText(todo.mainCategory)
                    listItemBinding.mainItemTxtSubCategory.setText(todo.subCategory)
                    listItemBinding.mainItemTxtDDay.setText(todo.rightTxt)
                }
            }
    }
