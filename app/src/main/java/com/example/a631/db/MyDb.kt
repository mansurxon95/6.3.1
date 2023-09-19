package com.example.a631.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.a631.NewsUser
import com.example.a631.Tabuser
import com.example.a631.Userlist

class MyDb(context:Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION),MyDbservis {


companion object{
    const val DB_NAME = "neme"
    const val DB_VERSION = 1


    const val TABLE_NEWS_NAME = "news"
    const val NEWS_ID = "id"
    const val  SARLAVXA = "sarlavxa"
    const val MATNI = "matni"
    const val TAB_NEWS_ID = "tabid"


    const val TABLE_TAB_NAME = "tabname"
    const val TAB_ID = "id"
    const val TAB_NAME = "tabname"
}

    override fun onCreate(db: SQLiteDatabase?) {

        val query1 = "create table $TABLE_NEWS_NAME($NEWS_ID integer not null primary key autoincrement unique," +
                "$SARLAVXA text not null," +
                "$MATNI text not null," +
                "$TAB_NEWS_ID   INTEGER NOT NULL," +
                "FOREIGN KEY($TAB_NEWS_ID) REFERENCES $TABLE_TAB_NAME($TAB_ID))"

        val query2 = "create table $TABLE_TAB_NAME ($TAB_ID integer not null primary key autoincrement," +
                "$TAB_NAME text not null)"

        db?.execSQL(query1)
        db?.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    override fun addnews(user: NewsUser) {
        val writableDatabase = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(SARLAVXA,user.sarlavxa)
        contentValues.put(MATNI,user.matni)
        contentValues.put(TAB_NEWS_ID,user.tabid)
        writableDatabase.insert(TABLE_NEWS_NAME,null,contentValues)
        writableDatabase.close()
    }

    override fun editnews(user: NewsUser): Int {
        val writableDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SARLAVXA, user.sarlavxa)
        contentValues.put(MATNI, user.matni)
        contentValues.put(TAB_NEWS_ID, user.tabid)
        return writableDatabase.update(TABLE_NEWS_NAME, contentValues, "${NEWS_ID}=?", arrayOf(user.id.toString()))

    }

    override fun deletnews(user: NewsUser) {
        Log.d("@@@@", "deletnews: ${user.id}")
        Log.d("@@@@", "deletnews: ${user.sarlavxa}")
        Log.d("@@@@", "deletnews: ${user.tabid}")
        val writableDatabase = this.writableDatabase
        writableDatabase.delete(TABLE_NEWS_NAME, "${NEWS_ID}=?", arrayOf("${user.id}"))
    }

    override fun getallnews(referenses1:Int):Userlist {

        var userlist = Userlist()
        var list2 = ArrayList<NewsUser>()
        val query = "SELECT $NEWS_ID, $SARLAVXA, $MATNI, $TAB_NEWS_ID from $TABLE_NEWS_NAME inner join $TABLE_TAB_NAME ON $NEWS_ID == $referenses1"

        val readableDatabase = this.readableDatabase
        val crusor = readableDatabase.rawQuery(query,null)
        if (crusor.moveToFirst()){
            do {
                val id = crusor.getInt(0)
                val sarlavxa = crusor.getString(1)
                val matni = crusor.getString(2)
                val tabnewid = crusor.getInt(3)
                list2.add(NewsUser(sarlavxa,matni,tabnewid))
              userlist = Userlist(list2)
            } while ( crusor.moveToNext())
        }

        return userlist

    }

    override fun getallnews2(): Userlist {
        var userlist = Userlist()
        var list2 = ArrayList<NewsUser>()
        val query = "SELECT * from $TABLE_NEWS_NAME"
        val readableDatabase = this.readableDatabase
        val crusor = readableDatabase.rawQuery(query,null)
        if (crusor.moveToFirst()){
            do {
                val id = crusor.getInt(0)
                val sarlavxa = crusor.getString(1)
                val matni = crusor.getString(2)
                val tabnewid = crusor.getInt(3)
                list2.add(NewsUser(id,sarlavxa,matni,tabnewid))
                userlist = Userlist(list2)
            } while ( crusor.moveToNext())
        }

        return userlist
    }

    override fun getalltab(): ArrayList<String> {
        val list = ArrayList<String>()
        val query = "select * from $TABLE_TAB_NAME"
        val readableDatabase = this.readableDatabase
        val crusor = readableDatabase.rawQuery(query,null)
        if (crusor.moveToFirst()){
            do {
                val id = crusor.getInt(0)
                val tabname = crusor.getString(1)
                list.add(tabname)
            } while ( crusor.moveToNext())
        }

        return list
    }

    override fun addtab(tabname:String) {
        val writableDatabase = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(TAB_NAME,tabname)
        writableDatabase.insert(TABLE_TAB_NAME,null,contentValues)
        writableDatabase.close()
    }
}