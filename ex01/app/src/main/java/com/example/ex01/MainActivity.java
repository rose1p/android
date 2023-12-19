package com.example.ex01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;

    int count = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub02);
        getSupportActionBar().setTitle("연습1");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn1) {
            Toast.makeText(MainActivity.this, "안녕하세요", Toast.LENGTH_SHORT).show();
        }else if(view.getId() == R.id.btn2) {
            Toast.makeText(MainActivity.this, "안녕하세요", Toast.LENGTH_LONG).show();
        }else if(view.getId() == R.id.btn3) {
            count++;
            Toast.makeText(MainActivity.this, "count" + count, Toast.LENGTH_LONG).show();
        }else {
            LinearLayout layout = (LinearLayout)View.inflate(MainActivity.this, R.layout.custom, null);
            Toast toast = new Toast(MainActivity.this);
            toast.setView(layout);
            toast.show();
        }
        @Override
        public

    }
}