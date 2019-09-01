package com.koko.opensourcedemo.gson

import com.google.gson.annotations.SerializedName

class User(name: String, var age: Int) {
    //alt+inset 移动到构造器中
    var name: String? = name
    @SerializedName("email_address")
    var emailAddress: String? = null

    override fun toString(): String {
        return "User(name=$name, age=$age, emailAddress=$emailAddress)"
    }


}