package com.example.app09_db1

import SqliteHelper
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app09_db1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var currentPosition: Int? = null // 현재 수정 중인 메모의 위치를 저장할 변수, null로 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // SqliteHelper 객체 생성
        val sqliteHelper = SqliteHelper(this, "memo", 1)

        // RecyclerAdapter 생성 및 초기화
        val adapter = RecyclerAdapter()
        adapter.helper = sqliteHelper // RecyclerAdapter에 SqliteHelper를 설정
        adapter.listData.addAll(sqliteHelper.selectMemo()) // 데이터베이스에서 모든 메모를 가져와 어댑터의 리스트에 추가

        // RecyclerView 설정
        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        // 버튼 클릭 이벤트 처리
        binding.buttonSave.setOnClickListener {
            val memoContent = binding.editMemo.text.toString()
            if (memoContent.isNotEmpty()) {
                if (binding.buttonSave.text.toString() == "저장") {
                    // 새로운 메모 추가
                    val memo = Memo(null, memoContent, System.currentTimeMillis())

                    // 메모를 데이터베이스에 저장
                    sqliteHelper.insertMemo(memo)

                    // EditText를 비우고, RecyclerView의 데이터를 새로고침
                    binding.editMemo.setText(null)
                    adapter.listData.clear()
                    adapter.listData.addAll(sqliteHelper.selectMemo())
                } else if (binding.buttonSave.text.toString() == "수정") {
                    // 기존 메모 수정
                    currentPosition?.let { pos ->
                        val memo = Memo(adapter.listData[pos].num, memoContent, System.currentTimeMillis())
                        sqliteHelper.updateMemo(memo)

                        // EditText를 비우고, RecyclerView의 데이터를 새로고침
                        binding.editMemo.setText(null)
                        adapter.listData.clear()
                        adapter.listData.addAll(sqliteHelper.selectMemo())

                        // 버튼 텍스트를 "저장"으로 변경
                        binding.buttonSave.setText("저장")
                    }
                }
                adapter.notifyDataSetChanged() // 어댑터의 데이터 변경을 UI에 반영
            }
        }

        // 아이템 클릭 이벤트 처리
        adapter.onItemClickLister = object : RecyclerAdapter.OnItemClickLister {
            override fun onItemClick(pos: Int) {
                // 클릭된 위치를 저장
                currentPosition = pos

                // EditText에 클릭된 아이템의 내용을 설정
                binding.editMemo.setText(adapter.listData[pos].content.toString())

                // 버튼 텍스트를 "수정"으로 변경
                binding.buttonSave.setText("수정")
            }
        }
    }
}
