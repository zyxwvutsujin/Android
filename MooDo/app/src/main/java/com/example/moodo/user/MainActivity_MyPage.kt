package com.example.moodo.user

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moodo.MainActivity
import com.example.moodo.R
import com.example.moodo.databinding.ActivityMainMyPageBinding
import com.example.moodo.db.MooDoClient
import com.example.moodo.db.MooDoUser
import com.example.moodo.sign.MainActivity_SignIn
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity_MyPage : AppCompatActivity() {
    lateinit var binding: ActivityMainMyPageBinding
    var user: MooDoUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userId = intent.getStringExtra("userId")

        loadUserInfo(userId!!)

        // 회원정보 변경 클릭 이벤트
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, MainActivity_UserEdit::class.java)
            user?.let {
                intent.putExtra("userName", it.name)
                intent.putExtra("userId", it.id)
            }
            startActivity(intent)
        }

        // 로그아웃 클릭 이벤트
        binding.btnLogout.setOnClickListener {
            logout()
        }

        // 회원 탈퇴 클릭 이벤트
        binding.btnDeleteAccount.setOnClickListener {
            deleteAccount(userId)
        }
    }

    private fun loadUserInfo(userId: String) {
        MooDoClient.retrofit.getUserInfo(userId).enqueue(object : Callback<MooDoUser> {
            override fun onResponse(call: Call<MooDoUser>, response: Response<MooDoUser>) {
                if (response.isSuccessful) {
                    user = response.body()
                    binding.userName.setText(user!!.name)
                    loadUserProfileImage(userId)
                } else {
                    Log.d("MooDoLog", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MooDoUser>, t: Throwable) {
                Log.d("MooDoLog", t.toString())
            }
        })
    }

    private fun loadUserProfileImage(userId: String) {
        MooDoClient.retrofit.getUserImg(userId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val inputStream = response.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.userProfile.setImageBitmap(bitmap)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("MooDoLog", t.toString())
            }
        })
    }

    private fun logout() {
        // 로그아웃시 로그인 화면으로
        val intent = Intent(this, MainActivity_SignIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun deleteAccount(userId: String) {
        MooDoClient.retrofit.deleteUser(userId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("MooDoLog", "Account deleted")
                    //회원 탈퇴 후 화면 이동
                    val intent = Intent(this@MainActivity_MyPage, MainActivity_SignIn::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("MooDoLog", "Failed to delete account: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("MooDoLog", "Error: $t")
            }
        })
    }
}
