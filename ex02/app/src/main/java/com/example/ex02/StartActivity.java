package com.example.ex02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24);
        getSupportActionBar().setTitle("메인메뉴");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        if(view.getId() == R.id.btn1) {
            intent = new Intent(this, MainActivity.class);
        }else if(view.getId() == R.id.btn2) {
            intent = new Intent(this, MainActivity2.class);
        }else if(view.getId() == R.id.btn3) {
            intent = new Intent(this, MainActivity4.class);
        }
        startActivity(intent);
    }
}