package com.example.ex06;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.nio.channels.AsynchronousChannelGroup;

public class MovieFragment extends Fragment {
    String query="배트맨";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_movie, container, false);
        new MovieThread().execute();
        return view;
    }

    class MovieThread extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String url="https://openapi.naver.com/v1/search/movie.xml";
            NaverAPI.con(url, query, 1);
            return null;
        }
    }
}