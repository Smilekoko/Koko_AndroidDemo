package com.koko.opensourcedemo.gson

import com.google.gson.Gson

fun main() {

    //1.Serialization
    val gson = Gson()
    print(gson.toJson(1)+"\n")

    //2.Deserialization
    val f: Boolean = gson.fromJson("false", Boolean::class.java)
    println(f)

    //3.生成JSON
    val user = User("怪盗kidou", 24)
    val jsonObject = gson.toJson(user) // {"name":"怪盗kidou","age":24}
    print(jsonObject+"\n")

    //4.解析JSON
    val jsonString = "{\"name\":\"怪盗kidou\",\"age\":24}"
    val user1 = gson.fromJson(jsonString, User::class.java)
    println(user1)

    //5.属性重命名 @SerializedName 注解的使用
    val jsonString1 = "{\"name\":\"怪盗kidou\",\"age\":24,\"email_address\":\"ikidou@example.com\"}"
    val user2 = gson.fromJson(jsonString1, User::class.java)
    println(user2)
}