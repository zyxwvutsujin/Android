package com.example.app09_db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app09_db.databinding.ActivityMain2Binding
import java.util.Calendar

class MainActivity2 : AppCompatActivity() {
    lateinit var bindnig:ActivityMain2Binding
    lateinit var sqLiteDatabase:SQLiteDatabase
    lateinit var myDBHelper:MyDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main2)
//        val binding = ActivityMain2Binding.inflate(layoutInflater)
        bindnig = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(bindnig.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         myDBHelper = MyDBHelper(this)

//        DB 설정
         sqLiteDatabase = myDBHelper.writableDatabase

        //    오늘 날짜
        val calender = Calendar.getInstance()
        val cYear = calender.get(Calendar.YEAR)
        val cMonth = calender.get(Calendar.MONTH) //0~11 0부터 인식하기때문에 +1 해줘야함
        val cDay = calender.get(Calendar.DAY_OF_MONTH) // 2024-08-29
        var diaryDate = "${cYear}${cMonth+1}${cDay}"
        Log.d("sql diaryDate :", diaryDate)
        // 오늘날짜 읽기 보여주기
        bindnig.editDiary.setText(readDiary(diaryDate)) //diaryDate : 오늘날짜
        bindnig.btnWrite.isEnabled = true
//        datePicker2에서 날짜 선택할때
        bindnig.datePicker2.init(cYear,cMonth, cDay) {datePicker, year, month,day->
//        선택된 날짜의 일기를 읽어오기
        diaryDate = "${year}${month+1}${day}" // datePicker2에서 선택 된 날짜
        bindnig.editDiary.setText(readDiary(diaryDate))
        }

//    쓰기버튼
        bindnig.btnWrite.setOnClickListener{
            sqLiteDatabase = myDBHelper.writableDatabase // 쓰기모드
            var sql = "INSERT INTO myDiary (diaryDate, content) VALUES ('$diaryDate', '${bindnig.editDiary.text}')"
            if (bindnig.btnWrite.text.equals("수정하기")){
                sql = "update myDiary set content ='${bindnig.editDiary.text}' " + "" +
                        "where diaryDate = '${diaryDate}'"
            }
            Log.d("sql insert :",sql)
            sqLiteDatabase.execSQL(sql)
            sqLiteDatabase.close()
            Toast.makeText(this,"입력완료", Toast.LENGTH_SHORT).show()
        }
    } //onCreate 여기까지

    private fun readDiary(diaryDate: String): String {
        var strResult=""
        sqLiteDatabase = myDBHelper.readableDatabase // 읽기 모드
        val sql = "select * from myDiary where diaryDate = '$diaryDate'"
        Log.d("sql select :",sql)
        var cursor:Cursor = sqLiteDatabase.rawQuery(sql,null)
        if (cursor.moveToNext()){
            strResult = cursor.getString(1)
            bindnig.btnWrite.text = "수정하기"
            Toast.makeText(this,"일기조회${strResult}", Toast.LENGTH_SHORT)
        }else{
            bindnig.btnWrite.text = "새로저장"
            bindnig.editDiary.setText("")
            bindnig.editDiary.hint = "일기 없음"
        }
        return strResult
    }

    //    내부 클래스
class MyDBHelper(context: Context):SQLiteOpenHelper(context, "myDB", null, 1) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " +
                "myDiary(diaryDate char(10), content varchar(500))")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, p1: Int, p2: Int) {
        sqLiteDatabase.execSQL("drop table if exists myDiary")
    }

}
}