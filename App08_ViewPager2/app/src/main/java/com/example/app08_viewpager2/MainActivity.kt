package com.example.app08_viewpager2

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.app08_viewpager2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
      //  setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var list = mutableListOf<DataPage>(
            DataPage(Color.RED, "1 page"),
            DataPage(Color.BLUE, "2 page"),
            DataPage(Color.GRAY, "3 page"),
            DataPage(Color.BLACK, "4 page"),
            DataPage(Color.YELLOW, "5 page"),
            DataPage(Color.CYAN, "6 page")
        )
        binding.viewPager2.adapter = ViewPagerAdapter(list)
        binding.btnToggle.setOnClickListener {
            when(binding.viewPager2.orientation){
                ViewPager2.ORIENTATION_VERTICAL->{
                    binding.btnToggle.text = "가로로 슬라이드"
                    binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                }
                ViewPager2.ORIENTATION_HORIZONTAL->{
                   binding.btnToggle.text="세로로 슬라이드"
                   binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
                }
            } //when
        }// btnToggle
    }
}