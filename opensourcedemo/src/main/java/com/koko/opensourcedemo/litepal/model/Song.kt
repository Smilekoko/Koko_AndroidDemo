package com.koko.opensourcedemo.litepal.model

import org.litepal.crud.LitePalSupport
import org.litepal.annotation.Column



class Song: LitePalSupport() {

    @Column(nullable = false)
    var name: String? = null

     var duration: Int = 0


}