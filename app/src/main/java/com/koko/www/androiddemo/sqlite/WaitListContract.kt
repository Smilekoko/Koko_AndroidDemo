package com.koko.www.androiddemo.sqlite

import android.provider.BaseColumns

/**
 * 创建SQLLite数据库的合约类，当数据库升级时，记得修改
 */
class WaitListContract {

    //用来定义sqlLite表的信息
    class WaitListEntry : BaseColumns {
        companion object {
            internal const val ID = "waitList"
            internal const val TABLE_NAME = "waitList"
            internal const val COLUMN_GUEST_NAME = "guestName"
            internal const val COLUMN_PARTY_SIZE = "partySizes"
            internal const val COLUMN_TIMESTAMP = "timeStamp"
        }
    }
}
