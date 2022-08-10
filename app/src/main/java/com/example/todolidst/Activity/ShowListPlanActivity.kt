package com.example.todolidst.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todolidst.DB.DAO.Plan
import com.example.todolidst.DB.DBHelper
import com.example.todolidst.DB.DBHelperCategory
import com.example.todolidst.MyCalendar
import com.example.todolidst.R
import com.example.todolidst.databinding.ActivityShowCategoryBinding
import com.example.todolidst.recyclerview.CategoryAdapter
import com.example.todolidst.recyclerview.CustomAdapter
import com.example.todolidst.recyclerview.SwipeHelperCallback

class ShowListPlanActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityShowCategoryBinding //viewBinder
    lateinit var adapter: CustomAdapter
    val TAG = "areum_showListPlan"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var db = DBHelper(this,null)
        adapter = CustomAdapter.getInstance(db)

        binding.fabPlus.setOnClickListener { //기본 FAB 클릭시
            val intent = Intent(this@ShowListPlanActivity, AddPlanActivity::class.java).apply {  }
            startActivity(intent)
        }

        //navigation 설정
        //https://developer.android.com/guide/navigation/navigation-ui?hl=ko

        //recyclerview setting
        setRecyclerview()
    }

    private fun setRecyclerview(){
        val db = DBHelper(this, null)
        adapter = CustomAdapter.getInstance(db)
        Log.v(TAG,"${adapter.itemCount}개 있음")
        val swipeHelperCallBack = SwipeHelperCallback(adapter).apply{
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 7 * 2) //1000 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallBack).attachToRecyclerView(binding.categoryRecyclerview)
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: Plan, pos: Int) {
            }
        })
        adapter.setOnItemEditClickListener(object : CustomAdapter.OnItemEditClickListener {
            override fun onItemEditClick(v: View, data: Plan, pos: Int) {
                if (!swipeHelperCallBack.isSlided()) {
                    Log.v(TAG,"swipe 되지 않음")
                    return
                }
                val mDialogView = LayoutInflater.from(this@ShowListPlanActivity).inflate(R.layout.dialog_edit_plan,null)
                val containter: LinearLayout = mDialogView.findViewById(R.id.dialog_spinnerContainer)
                val btnOK: Button = mDialogView.findViewById(R.id.dialog_buttonOK)
                containter.visibility = View.VISIBLE
                val mBuilder = AlertDialog.Builder(this@ShowListPlanActivity)
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
            override fun onItemDelClick(v: View, data: Plan, pos: Int) {
                if (!swipeHelperCallBack.isSlided()) {
                    Log.v(TAG,"swipe 되지 않음")
                    return
                }
                Log.v(TAG,"${data.id} : 삭제버튼 클릭")
                Toast.makeText(this@ShowListPlanActivity,"${data.id}가 삭제됨", Toast.LENGTH_SHORT).show()
                //TODO: 데이터 삭제
                adapter.notifyDataSetChanged()
            }
        })
        binding.categoryRecyclerview.adapter = adapter
    }
}