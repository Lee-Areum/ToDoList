package com.example.todolidst

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(//main(일별) recyclerivew
    private val dayToDoList: ArrayList<DayToDo>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var clickListener: OnItemClickListener? = null
    private var longClickListener: OnLongClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View, data: DayToDo, pos: Int)
    }

    interface OnLongClickListener {
        fun onLongClick(v: View, data: DayToDo, pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }

    fun setOnLongClickListener(listener: OnLongClickListener) {
        this.longClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder { //recyclerview 생성시 호출
        val v = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) { //특정 position의 데이터를 bind하기 위해 호출
        holder.bindItem(dayToDoList[position])
    }

    override fun getItemCount(): Int { //recyclerview 아이탬 개수를 얻기위해 호출
        return dayToDoList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //recyclerview의 한 아이탬을 대변함
        val check: CheckBox = view.findViewById(R.id.main_item_check)
        val txt_main: TextView = view.findViewById(R.id.main_item_txtMainCategory)
        val txt_sub: TextView = view.findViewById(R.id.main_item_txtSubCategory)
        val txt_date: TextView = view.findViewById(R.id.mainItem_txt_date)
        val txt_dday: TextView = view.findViewById(R.id.mainItem_txt_dDay)

        fun bindItem(todo: DayToDo) {
            check.setChecked(todo.isDone)
            txt_main.setText(todo.strList[0])
            txt_sub.setText(todo.strList[1])
            if (todo.strList.size == 3) { //D-day
                txt_dday.setText("D -")
                txt_date.setText(todo.strList[2])
            } else { // 3/5
                txt_dday.setText("${todo.strList[2]} /")
                txt_date.setText(todo.strList[3])
            }
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    clickListener?.onItemClick(itemView, todo, pos)
                }
                itemView.setOnLongClickListener {
                    longClickListener?.onLongClick(itemView, todo, pos)
                    return@setOnLongClickListener true
                }
            }
        }
    }
}
