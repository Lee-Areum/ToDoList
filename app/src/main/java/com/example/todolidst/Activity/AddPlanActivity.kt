package com.example.todolidst.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolidst.databinding.ActivityAddPlanBinding

class AddPlanActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityAddPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}