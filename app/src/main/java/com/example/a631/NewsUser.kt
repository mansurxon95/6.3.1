package com.example.a631

import java.io.Serializable

class NewsUser : Serializable {

    var id:Int?=null
    var sarlavxa:String?=null
    var matni:String?=null
    var tabid:Int?=null

    constructor(sarlavxa: String?, matni: String?, tabid: Int?) {
        this.sarlavxa = sarlavxa
        this.matni = matni
        this.tabid = tabid
    }

    constructor(id: Int?, sarlavxa: String?, matni: String?, tabid: Int?) {
        this.id = id
        this.sarlavxa = sarlavxa
        this.matni = matni
        this.tabid = tabid
    }

}