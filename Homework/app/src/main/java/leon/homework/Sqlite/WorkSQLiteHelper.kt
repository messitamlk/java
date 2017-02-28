package leon.homework.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by BC on 2017/2/20 0020.
 */
class WorkSQLiteHelper(context: Context, name:String="Work.db", factory: SQLiteDatabase.CursorFactory?=null, version:Int=1): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}