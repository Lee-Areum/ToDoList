package com.example.todolidst.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolidst.DB.DAO.Plan
import com.example.todolidst.DB.DBHelper
import com.example.todolidst.MyCalendar
import com.example.todolidst.R


class CustomAdapter(private var db : DBHelper) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //main(일별) recyclerivew
    private var planList: ArrayList<Plan> = getData()

    private val TAG : String = "areum_Adapter"
    private var clickListener: OnItemClickListener? = null
    private var editclickListener: OnItemEditClickListener? = null
    private var deleteclickListener: OnItemDelClickListener? = null

    fun setDB(database:DBHelper){
        db = database
    }
    companion object{
        private var instance : CustomAdapter?= null
        fun getInstance(database : DBHelper):CustomAdapter {
            if (instance == null) {
                instance = CustomAdapter(database)
            } else {
                instance!!.setDB(database)
            }
            return instance!!
        }
    }
    @SuppressLint("Range")
    private fun getData() : ArrayList<Plan>{
        val cursor = db.getAllPlan()
        val list : ArrayList<Plan> = ArrayList()
        cursor!!.moveToFirst()
        do{
            val p = Plan(
                id = cursor.getInt(cursor.getColumnIndex(DBHelper.ID)),
                categoryID =  cursor.getInt(cursor.getColumnIndex(DBHelper.CATEGORY_ID)),
                category =cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY)),
//                category= "test",
                date = cursor.getLong(cursor.getColumnIndex(DBHelper.DATE)),
                content = cursor.getString(cursor.getColumnIndex(DBHelper.CONTENT)),
                isDone = cursor.getInt(cursor.getColumnIndex(DBHelper.iS_DONE))
            )
            list.add(p)
        }while(cursor.moveToNext())
//        Log.v(TAG,"getlist size : ${list.size}")
        cursor.close()
        return list
    }

    fun insertPlan(plan : Plan) : Int{
        val flag = db.insertPlan(plan)
        planList = getData()
        return flag
    }

    interface OnItemClickListener { //item 클릭
        fun onItemClick(v: View, data: Plan, pos: Int)
    }
    interface OnItemEditClickListener { //수정 클릭
        fun onItemEditClick(v: View, data: Plan, pos: Int)
    }
    interface OnItemDelClickListener {
        fun onItemDelClick(v: View, data: Plan, pos: Int)
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
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) { //특정 position의 데이터를 bind하기 위해 호출
        holder.bindItem(planList[position])
    }

    override fun getItemCount(): Int { //recyclerview 아이탬 개수를 얻기위해 호출
        return planList.size
    }

//    private fun createDayToDoList() : ArrayList<Plan> {
//        return arrayListOf<Plan>(
//            Plan(
//                0,
//                System.currentTimeMillis(),
//                1,
//                "소단원1",
//                "대단원1",
//                0,
//            ),
//            Plan(
//                1,
//                System.currentTimeMillis(),
//                2,
//                "소단원1",
//                "대단원2",
//                0,
//            ),
//            Plan(
//                2,
//                System.currentTimeMillis(),
//                1,
//                "소단원2",
//                "대단원1",
//                0,
//            ),
//        )
//    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //recyclerview의 한 아이탬을 대변함
        private val txt_category: TextView = view.findViewById(R.id.main_item_category)
        private val txt_content: TextView = view.findViewById(R.id.main_item_content)
        private val txt_date: TextView = view.findViewById(R.id.mainItem_txt_date)
        private val txt_dday: TextView = view.findViewById(R.id.mainItem_txt_dDay)
        private val txt_edit : TextView = view.findViewById(R.id.item_edit)
        private val txt_delete : TextView = view.findViewById(R.id.item_delete)

        fun bindItem(todo: Plan) {
            txt_category.text = todo.category
            txt_content.text = todo.content

            txt_dday.text = "D -"
            txt_date.text  = MyCalendar.getInstance().getDday(todo.date) //d-day 설정
            val pos = todo.id
            if (pos < 0) {
                itemView.setOnClickListener {
                    clickListener?.onItemClick(itemView, todo, pos)
                }
            }
            txt_edit.setOnClickListener{
                editclickListener?.onItemEditClick(itemView,todo,pos)
//                Log.v(TAG,"${todo.id} : 수정버튼 클릭")
            }
            txt_delete.setOnClickListener{
                //TODO : 삭제하기
                deleteclickListener?.onItemDelClick(itemView,todo,pos)
            }
        }
    }
}
