package com.example.app10_member

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app10_member.databinding.ActivityMainBinding
import com.example.app10_member.databinding.CustomMemberBinding
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var memberAdapter:MemberAdapter? = null
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
        val memberList= mutableListOf<Member>()
        memberAdapter = MemberAdapter(memberList)
        binding.recyclerView.adapter = memberAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

//        앱 시작시 전체 리스트
        loadMemberList()

//        adapter
        memberAdapter = MemberAdapter(memberList)
        binding.recyclerView.adapter = memberAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        var position = 0
        var id:Long? = null
        var insertName:String? = null
        var insertPhone:String? = null
        var insertEmail:String? = null

        var name:String? = null
        var phone:String? = null
        var email:String? = null

        // 추가 intent
        val activityInsert = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                insertName = it.data?.getStringExtra("name").toString()
                insertPhone = it.data?.getStringExtra("phone").toString()
                insertEmail = it.data?.getStringExtra("email").toString()

                Log.d("response insert data :", "${insertName} / ${insertPhone} / ${insertEmail}")

                val m = Member(0, insertName!!, insertPhone!!, insertEmail!!)
                MemberClient.retrofit.save(m).enqueue(object :retrofit2.Callback<Member>{
                    override fun onResponse(call: Call<Member>, response: Response<Member>) {
                        Log.d("response", response.body().toString())
                        // null 이 아닐때 넣어라
                        response.body()?.let { it1 -> memberAdapter!!.addItem(it1) }
                    }

                    override fun onFailure(call: Call<Member>, t: Throwable) {
                        Log.d("response fail", t.toString())
                    }
                })
            }
        }


        // 추가 버튼 클릭 시
        binding.floatBtn.setOnClickListener {
            val intent = Intent(this, MainActivity_Insert::class.java)
            activityInsert.launch(intent)
        }
    }


    private fun loadMemberList() {
        MemberClient.retrofit.findAll().enqueue(object : retrofit2.Callback<List<Member>> {
            override fun onResponse(call: Call<List<Member>>, response: Response<List<Member>>) {
                memberAdapter?.memberList = response.body() as MutableList<Member>
                memberAdapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Member>>, t: Throwable) {
                Log.d("실패함", t.toString())
            }

        })
    }
}