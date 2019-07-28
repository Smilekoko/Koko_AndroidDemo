package com.koko.opensourcedemo.retrofit.bean

class RepositoryBean {
    var full_name: String? = null
    var html_url: String? = null

    var contributions: Int = 0

    override fun toString(): String {
        return "$full_name ($contributions)"
    }
}