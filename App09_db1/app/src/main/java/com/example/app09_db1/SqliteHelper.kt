import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app09_db1.Memo


class SqliteHelper(context: Context, name:String, version:Int):
    SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        val sql = "create table if not exists " +
                "memo(num Integer primary key," +
                "content text," +
                "datetime Integer)"
        sqLiteDatabase?.execSQL(sql)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sqLiteDatabase?.execSQL("drop table if exists memo")
    }
    // 전체 보기
    fun selectMemo(): MutableList<Memo> {
        var list = mutableListOf<Memo>()
        val sql = "select * from memo"

        val readDB = readableDatabase
        val cursor = readDB.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            list.add(Memo(cursor.getLong(0), cursor.getString(1), cursor.getLong(2)))
        }
        readDB.close()
        cursor.close()

        return list
    }

    // 추가
    fun insertMemo(memo: Memo) {
        val values = ContentValues()
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        val writeDB = writableDatabase
        writeDB.insert("memo", null, values)
        writeDB.close()
    }

    // 수정
    fun updateMemo(memo: Memo){
        val values = ContentValues()
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        val writeDB = writableDatabase
        writeDB.update("memo", values,"num=${memo.num}",null)
        writeDB.close()
    }

    // 삭제
    fun deleteMemo(memo: Memo){
        val sql = "delete from memo where num= ${memo.num}"
        val wd = writableDatabase
        wd.execSQL(sql)
        wd.close()
    }
}