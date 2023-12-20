package com.example.ex08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertActivity extends AppCompatActivity {

    FirebaseUser user;

    FirebaseFirestore db;

    EditText title, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle("상품등록:" + user.getEmail());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTitle = title.getText().toString();
                String strPrice = price.getText().toString();
                if(strTitle.equals("") || strPrice.equals("")) {
                    Toast.makeText(InsertActivity.this, "상품이름과 가격을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder box = new AlertDialog.Builder(InsertActivity.this);
                    box.setTitle("질의");
                    box.setMessage("상품을 등록하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShopVO vo = new ShopVO();
                            vo.setTitle(strTitle);
                            vo.setDate(sdf.format(new Date()));
                            vo.setEmail(user.getEmail());
                            vo.setPrice(Integer.parseInt(strPrice));
                            insertShop(vo);
                        }
                    });
                    box.setNegativeButton("아니요", null);
                    box.show();
                }
            }
        });
    }

    //상품등록
    public void insertShop(ShopVO vo) {
        db.collection("shop")
                .add(vo)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(InsertActivity.this, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(InsertActivity.this, "상품등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}