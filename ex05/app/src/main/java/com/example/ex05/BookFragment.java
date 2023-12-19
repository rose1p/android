package com.example.ex05;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class BookFragment extends Fragment {

    String query = "안드로이드";

    int start = 1;

    int page = 1;

    int list_page = 0;

    BookAdapter adapter = new BookAdapter();

    ArrayList<HashMap<String, Object>> array = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        new BookThread().execute();

        RecyclerView list = view.findViewById(R.id.list);
        list.setAdapter(adapter);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(manager);

        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtQuery = view.findViewById(R.id.query);
                query = edtQuery.getText().toString();
                array = new ArrayList<>();
                new BookThread().execute();
            }
        });

        view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page < list_page) {
                    page++;
                    start = (page - 1) * 10 + 1;
                    new BookThread().execute();
                }else {
                    Toast.makeText(getActivity(), "마지막페이지 입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    class BookThread extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = "https://openapi.naver.com/v1/search/book.json";
            String result = NaverAPI.con(url, query, start);
            System.out.println("도서결과:" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            BookParser(s);
            System.out.println("도서 데이터 개수:" + array.size());
            adapter.notifyDataSetChanged();
        }
    }

    public void BookParser(String result) {
        try {
            JSONObject objTotal = new JSONObject(result);

            int total = Integer.parseInt(objTotal.getString("total"));
            list_page = total/10;
            if(total/10. != list_page) {
                list_page = list_page + 1;
            }

            System.out.println("..............total:" + total + "last_page:" + list_page);

            JSONArray jArray = new JSONObject(result).getJSONArray("items");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<>();
                map.put("title", obj.getString("title"));
                map.put("link", obj.getString("link"));
                map.put("price", obj.getInt("discount"));
                map.put("image", obj.getString(("image")));
                array.add(map);
            }
        } catch (Exception e) {
            System.out.println("파싱오류:" + e.toString());
        }
    }

    class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

        @NonNull
        @Override
        public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_shop, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
            HashMap<String, Object> map = array.get(position);
            holder.title.setText(Html.fromHtml(map.get("title").toString()));
            int intPrice = Integer.parseInt(map.get("price").toString());
            DecimalFormat df = new DecimalFormat("#,###원");
            holder.price.setText(df.format(intPrice));
            String strImage = map.get("image").toString();
            if (strImage.equals("")) {
                holder.image.setImageResource(R.drawable.baseline_book_24);
            } else {
                Picasso.with(getActivity()).load(strImage).into(holder.image);
            }
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("link", map.get("link").toString());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, price;

            ImageView image;

            CardView item;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                price = itemView.findViewById(R.id.price);
                image = itemView.findViewById(R.id.image);
                item = itemView.findViewById(R.id.item);
            }
        }
    }
}