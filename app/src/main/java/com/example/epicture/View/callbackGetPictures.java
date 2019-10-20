package com.example.epicture.View;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epicture.Modele.MyPictures;
import com.example.epicture.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class callbackGetPictures implements okhttp3.Callback {
    private Activity activity;
    private int nb;
    final List<MyPictures> images = new ArrayList<MyPictures>();

    public callbackGetPictures(Activity _activity) {
        super();
        activity = _activity;
        nb = 0;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.w("GetPictures Error", "Fail request");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response != null) {
            JSONObject data;
            JSONArray items;
            try {
                data = new JSONObject(response.body().string());
                items = data.getJSONArray("data");
                for (int i = 0; i < items.length(); ++i) {
                    JSONObject item = items.getJSONObject(i);
                    MyPictures image = new MyPictures();

                    image.id = item.getString("id");
                    image.title = item.getString("title");
                    image.link = item.getString("link");
                    if (image.link.contains("https://i.imgur.com/"))
                        images.add(image);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            nb++;
            if (nb >= 7)
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        render(images);
                    }
                });
        }
    }

    private void render(final List<MyPictures> pictures) {
        RecyclerView rv = activity.findViewById(R.id.rv_of_photos);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        RecyclerView.Adapter<pictureViewHolder> adapter = new RecyclerView.Adapter<pictureViewHolder>() {
            @Override
            public pictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                pictureViewHolder pvh = new pictureViewHolder(activity.getLayoutInflater().inflate(R.layout.item, null));
                pvh.photo = pvh.itemView.findViewById(R.id.photo);
                pvh.title = pvh.itemView.findViewById(R.id.title);
                return pvh;
            }

            @Override
            public void onBindViewHolder(pictureViewHolder holder, int position) {
                Picasso.with(activity).load("https://i.imgur.com/" + pictures.get(position).id + ".jpg").into(holder.photo);
                holder.title.setBackgroundColor(Color.rgb(216, 81, 40));
                holder.title.setTextColor(Color.WHITE);
                holder.title.setText(pictures.get(position).title);
            }

            @Override
            public int getItemCount() {
                return pictures.size();
            }
        };
        rv.setAdapter(adapter);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 16; // Gap of 16px
            }
        });
    }

    private static class pictureViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView title;
        public pictureViewHolder(View itemView) {
            super(itemView);
        }
    }
}