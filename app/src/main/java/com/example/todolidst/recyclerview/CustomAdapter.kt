package com.example.todolidst.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolidst.DayToDo
import com.example.todolidst.R


class CustomAdapter(//main(일별) recyclerivew
    private val dayToDoList: ArrayList<DayToDo>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val TAG : String = "areum_Adapter"
    private var clickListener: OnItemClickListener? = null
    private var editclickListener: OnItemEditClickListener? = null
    private var deleteclickListener: OnItemDelClickListener? = null

    interface OnItemClickListener { //item 클릭
        fun onItemClick(v: View, data: DayToDo, pos: Int)
    }
    interface OnItemEditClickListener { //수정 클릭
        fun onItemEditClick(v: View, data: DayToDo, pos: Int)
    }
    interface OnItemDelClickListener {
        fun onItemDelClick(v: View, data: DayToDo, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {  //item 클릭
        this.clickListener = listener
    }
    fun setOnItemEditClickListener(listener: OnItemEditClickListener) { //수정 클릭
        this.editclickListener = listener
    }
    fun setOnItemDelClickListener(listener: OnItemDelClickListener) { //수정 클릭
        this.deleteclickListener = listener
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
        val txt_main: TextView = view.findViewById(R.id.main_item_txtMainCategory)
        val txt_sub: TextView = view.findViewById(R.id.main_item_txtSubCategory)
        val txt_date: TextView = view.findViewById(R.id.mainItem_txt_date)
        val txt_dday: TextView = view.findViewById(R.id.mainItem_txt_dDay)
        val txt_edit : TextView = view.findViewById(R.id.item_edit)
        val txt_delete : TextView = view.findViewById(R.id.item_delete)

        fun bindItem(todo: DayToDo) {
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
            }
            txt_edit.setOnClickListener{
                editclickListener?.onItemEditClick(itemView,todo,pos)
                Log.v(TAG,"${todo.idNo} : 수정버튼 클릭")
            }
            txt_delete.setOnClickListener{
                //TODO : 삭제하기
                deleteclickListener?.onItemDelClick(itemView,todo,pos)
            }
        }
    }
}
