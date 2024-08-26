package com.example.app08_viewpager2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app08_viewpager2.databinding.Activity3TablayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity3_tablayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity3_tablayout)
        val binding = Activity3TablayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //어댑터 생성
         val contentAdapter3 = ContentAdapter3(this)
        //어댑터연결
        binding.viewPager3.adapter = contentAdapter3

        val tabElement:List<String> = mutableListOf("첫번째탭", "두번째탭", "세번째탭")
       //탭과 viewPager 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager3){tab, position->
            val textView = TextView(this@MainActivity3_tablayout)
            textView.text = tabElement[position]
            tab.customView = textView
       }.attach()
    }
}




