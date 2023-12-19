package com.example.ex05;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieFragment extends Fragment {
    List<HashMap<String, Object>> array=new ArrayList<>();
    MovieAdapetr adapetr = new MovieAdapetr();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shop, container, false);
        view.findViewById(R.id.more).setVisibility(View.GONE);
        view.findViewById(R.id.query).setVisibility(View.GONE);
        view.findViewById(R.id.search).setVisibility(View.GONE);
        new MovieThread().execute();

        RecyclerView list=view.findViewById(R.id.list);
        list.setAdapter(adapetr);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        list.setLayoutManager(manager);
        return view;
    }

    class MovieThread extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                Document doc= Jsoup.connect("http://www.cgv.co.kr/movies/?lt=1&ft=0").get();
                Elements es= doc.select(".sect-movie-chart ol");
                for(Element e:es.select("li")){
                    String rank=e.select(".rank").text();
                    String title=e.select(".title").text();
                    String image=e.select("img").attr("src");
                    String link="http://www.cgv.co.kr/" + e.select(".link-reservation").attr("href");
                    HashMap<String, Object> map=new HashMap<>();
                    map.put("rank", rank);
                    map.put("title", title);
                    map.put("image", image);
                    map.put("link", link);
                    array.add(map);
                }
            }catch (Exception e){
                System.out.println("스크랩핑오류:" + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapetr.notifyDataSetChanged();
        }
    }

    class MovieAdapetr extends RecyclerView.Adapter<MovieAdapetr.ViewHolder>{
        @NonNull
        @Override
        public MovieAdapetr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieAdapetr.ViewHolder holder, int position) {
            HashMap<String, Object> map=array.get(position);
            holder.title.setText(map.get("title").toString());
            holder.rank.setText(map.get("rank").toString());
            Picasso.with(getActivity()).load(map.get("image").toString()).into(holder.image);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), WebActivity.class);
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
            ImageView image;
            TextView title, rank;
            Button btn;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                title=itemView.findViewById(R.id.title);
                rank = itemView.findViewById(R.id.rank);
                btn = itemView.findViewById(R.id.btn);
            }
        }
    }
}