package com.example.ex03;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AddressHelper extends SQLiteOpenHelper {

    public AddressHelper(@Nullable Context context) {
        super(context, "address.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table address(_id integer primary key autoincrement, name text, phone text, juso text, photo text)");
        db.execSQL("insert into address(name, phone, juso, photo) values('홍길동', '010-1010-1010', '가산 디지털단지 디지털로123 지식산업단지', '')");
        db.execSQL("insert into address(name, phone, juso, photo) values('고길동', '010-2020-2020', '경기도 시흥시 신청동 두산아파트', '')");
        db.execSQL("insert into address(name, phone, juso, photo) values('중길동', '010-3030-3030', '인천 서구 소래포구 시장 어딘가', '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
