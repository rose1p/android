package com.example.ex02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ProductVO> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("연습1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //데이터 생성
        array = new ArrayList<>();
        ProductVO vo = new ProductVO(1, "상섬 디스포크 냉장고", R.drawable.a1, 4500000);
        array.add(vo);
        vo = new ProductVO(2, "핫셀블라드 카메라", R.drawable.a2, 3600000);
        array.add(vo);
        vo = new ProductVO(3, "LG냉장고", R.drawable.a3, 1500000);
        array.add(vo);
        vo = new ProductVO(4, "Apell ipad", R.drawable.a4, 3000000);
        array.add(vo);

        //어뎁터 생성
        MyAdapter adapter = new MyAdapter();

        //ListView 어뎁터 연결
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    //어뎁터 생성
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.item, parent, false);
            ProductVO vo = array.get(position);
            ImageView photo = layout.findViewById(R.id.photo);
            photo.setImageResource(vo.getPhoto());
            TextView name = layout.findViewById(R.id.name);
            name.setText(vo.getName());
            TextView price = layout.findViewById(R.id.price);
            DecimalFormat df = new DecimalFormat("#,###원");
            price.setText(df.format(vo.getPrice()));
            Button btn = layout.findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, vo.getName() + "상품을 주문합니다.", Toast.LENGTH_SHORT).show();
                }
            });
            // 아이템을 선택한 경우
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                    intent.putExtra("name", vo.getName());
                    intent.putExtra("price", vo.getPrice());
                    intent.putExtra("photo", vo.getPhoto());
                    startActivity(intent);
                }
            });
            return layout;
        }
    } // onCreate

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}// Activity