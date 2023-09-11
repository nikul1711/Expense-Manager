package com.example.expense_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expense_manager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvDemo.setOnClickListener {

        }
    }
}