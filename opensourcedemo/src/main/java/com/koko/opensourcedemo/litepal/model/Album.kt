package com.koko.opensourcedemo.litepal.model

import org.litepal.crud.LitePalSupport
import org.litepal.annotation.Column


/**
 * 定义LitePal的model类
 * litepal支持的实体类字段映射类型为 int，long，double，float，byte[]，boolean，String，Date；不支持String[]数组型
 */
class Album : LitePalSupport() {
    @Column(unique = true, defaultValue = "unknown")
    var name: String? = null

    var price: Float = 0.toFloat()

    val songs = ArrayList<Song>()

}