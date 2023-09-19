package com.example.a631

import java.io.Serializable

class Userlist:Serializable{
    var list:ArrayList<NewsUser>?=null

    constructor(list: ArrayList<NewsUser>?) {
        this.list = list
    }

    constructor()

}