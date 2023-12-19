package com.example.ex02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class InsertActivity extends AppCompatActivity {

    DBHelper dbHelper;

    SQLiteDatabase db;

    EditText name, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        getSupportActionBar().setTitle("상품등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();
                String strPrice = price.getText().toString();
                if (strName.equals("") || strPrice.equals("")) {
                    new AlertDialog.Builder(InsertActivity.this)
                            .setTitle("경고알림")
                            .setMessage("상품명과 가격을 입력해주세요")
                            .setPositiveButton("확인", null)
                            .show();
                }else {
                    String sql = "insert into product(name, price) values(";
                    sql += "'" + strName + "',";
                    sql += strPrice + ")";
                    db.execSQL(sql);
                    finish();
                }
            }
        });
    }
}