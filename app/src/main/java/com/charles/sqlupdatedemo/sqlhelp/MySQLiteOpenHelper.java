package com.charles.sqlupdatedemo.sqlhelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carson_Ho on 16/11/18.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {


    //数据库版本号
    private static Integer Version = 4;


    //在SQLiteOpenHelper的子类当中，必须有该构造函数
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
    }
    //参数说明
    //context:上下文对象
    //name:数据库名称
    //param:factory
    //version:当前数据库的版本，值必须是整数并且是递增的状态

    public MySQLiteOpenHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }


    public MySQLiteOpenHelper(Context context, String name) {
        this(context, name, Version);
    }

    //当数据库创建的时候被调用
    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("创建数据库和表");
        //创建了数据库并创建一个叫records的表
        //SQLite数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
        String sql = "create table user(id int primary key,name varchar(200))";
        //execSQL用于执行SQL语句
        //完成数据库的创建
        db.execSQL(sql);
        //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
    }

    //数据库升级时调用
    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade（）方法

    private  static final String TEXT_TYPE = " TEXT";
    private static final String ALTER_TABLE_MESSAGES_ADD_COLUMN_BUTTON_COPY = "ALTER TABLE user ADD COLUMN " + "age" + TEXT_TYPE;
    private static final String DROP_TABLE_USER = "DROP TABLE user";
    private static final String CREATE_TABLE_USER = "create table user(id int primary key,name varchar(200),age varchar(200),sex varchar(200))";

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDb, int oldVersion, int newVersion) {
        System.out.println("更新数据库版本为:" + newVersion);
        if (oldVersion == 1) {  //版本升级添加字段(以前的数据保留)
            sqLiteDb.beginTransaction();
            sqLiteDb.execSQL(ALTER_TABLE_MESSAGES_ADD_COLUMN_BUTTON_COPY);
            sqLiteDb.setTransactionSuccessful();
            sqLiteDb.endTransaction();
            return;
        }

        if(oldVersion == 3){ //直接删表重新建立(以前的数据会被删掉)
            sqLiteDb.beginTransaction();
            sqLiteDb.execSQL(DROP_TABLE_USER);
            sqLiteDb.execSQL(CREATE_TABLE_USER);
            sqLiteDb.setTransactionSuccessful();
            sqLiteDb.endTransaction();
            return;
        }
    }

}
