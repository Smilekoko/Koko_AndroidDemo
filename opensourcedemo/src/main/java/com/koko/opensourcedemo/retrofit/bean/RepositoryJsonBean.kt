package com.koko.opensourcedemo.retrofit.bean

import com.google.gson.annotations.SerializedName

data class RepositoryJsonBean(
        @SerializedName("full_name")
        val fullName: String){
}