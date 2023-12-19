package com.example.ex01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    int count = 0;
    TextView textCount;

    TextView textFruit;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle("연습2");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        textCount = findViewById(R.id.count);
        textFruit = findViewById(R.id.fruit);

        findViewById(R.id.btnin).setOnClickListener(onClick);
        findViewById(R.id.btnde).setOnClickListener(onClick);

        findViewById(R.id.btnin).setOnLongClickListener(onLingClick);
        findViewById(R.id.btnde).setOnLongClickListener(onLingClick);

        findViewById(R.id.btnApple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textFruit.setText("사과");
            }
        });

        findViewById(R.id.btnOrange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textFruit.setText("오렌지");
            }
        });
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnin) {
                count++;
                textCount.setText(String.valueOf(count));
            } else if (v.getId() == R.id.btnde) {
                count--;
            }
            textCount.setText(String.valueOf(count));
        }
    };

    View.OnLongClickListener onLingClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == R.id.btnin) {
                count = 100;
            } else {
                count = 0;
            }
            textCount.setText(String.valueOf(count));
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.jjajang) {
            Toast.makeText(this, "짜장은 달콤해", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.jjanbbong) {
            Toast.makeText(this, "짬뽕은 매워", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.udong) {
            Toast.makeText(this, "우동은 시원해", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.mandoo) {
            Toast.makeText(this, "만두는 고소해", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}