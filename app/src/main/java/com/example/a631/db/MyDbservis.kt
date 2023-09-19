package com.example.a631.db

import com.example.a631.NewsUser
import com.example.a631.Userlist

interface MyDbservis {

    fun addnews(user:NewsUser)
    fun editnews(user:NewsUser):Int
    fun deletnews(user:NewsUser)
    fun getallnews(referenses1:Int): Userlist
    fun getallnews2(): Userlist



    fun getalltab():ArrayList<String>
    fun addtab(tabname:String)
}