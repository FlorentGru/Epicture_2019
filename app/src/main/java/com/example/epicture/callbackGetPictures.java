package com.example.epicture;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
            if (nb >= 10)
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
        RecyclerView.Adapter<PhotoVH> adapter = new RecyclerView.Adapter<PhotoVH>() {
            @Override
            public PhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
                PhotoVH vh = new PhotoVH(activity.getLayoutInflater().inflate(R.layout.item, null));
                vh.photo = vh.itemView.findViewById(R.id.photo);
                vh.title = vh.itemView.findViewById(R.id.title);
                return vh;
            }

            @Override
            public void onBindViewHolder(PhotoVH holder, int position) {
                Picasso.with(activity).load("https://i.imgur.com/" + pictures.get(position).id + ".jpg").into(holder.photo);
//                Picasso.with(activity).load(pictures.get(position).link).into(holder.photo);
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

    private static class PhotoVH extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView title;
        public PhotoVH(View itemView) {
            super(itemView);
        }
    }
}