package com.example.app08_1

import ContentAdapter
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app08_1.R
import com.example.app08_1.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //어댑터 생성
        val contentAdapter3 = ContentAdapter(this)
        //어댑터연결
        binding.viewPager3.adapter = contentAdapter3

        val tabElement:List<String> = mutableListOf("Tab1", "Tab2", "Tab3", "Tab4", "Tab5")
        //탭과 viewPager 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager3){tab, position->
            val textView = TextView(this)
            textView.text = tabElement[position]
            tab.customView = textView
        }.attach()
    }
}




