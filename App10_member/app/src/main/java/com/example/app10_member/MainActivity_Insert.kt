package com.example.app10_member

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app10_member.databinding.ActivityMainInsertBinding

class MainActivity_Insert : AppCompatActivity() {
    lateinit var binding: ActivityMainInsertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 추가
        binding.btnSave.setOnClickListener {
            intent.putExtra("name", binding.edtName.text.toString())
            intent.putExtra("phone", binding.edtPhone.text.toString())
            intent.putExtra("email", binding.edtEmail.text.toString())

            setResult(RESULT_OK, intent)
            finish()
        }

        // 닫기
        binding.btnBack.setOnClickListener {
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }
}