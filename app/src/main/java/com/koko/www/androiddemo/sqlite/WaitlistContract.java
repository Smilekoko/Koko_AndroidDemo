package com.koko.www.androiddemo.sqlite;

import android.provider.BaseColumns;

/**
 * 创建SQLlite数据库的合约类，当数据库升级时，记得修改
 */
public  class  WaitlistContract {

    //用来定义sqllite表的信息
    public static final class WaitlistEntry implements BaseColumns {
        static final String TABLE_NAME = "waitlist";
        static final String COLUMN_GUEST_NAME = "guestName";
        static final String COLUMN_PARTY_SIZE = "partySizes";
        static final String COLUMN_TIMESTAMP  = "timeStamp";
    }
}
