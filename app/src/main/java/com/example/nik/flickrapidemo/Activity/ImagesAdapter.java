package com.example.nik.flickrapidemo.Activity;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> imagesList;

    public ImagesAdapter(List<String> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return imagesList != null ? imagesList.size() : 0;
    }

    public void addItemsToList(List<String> list) {
        if (imagesList != null && list != null || list.size() > 0) {
            int positionStart = imagesList.size();
            imagesList.addAll(list);
            notifyItemRangeInserted(positionStart, list.size());
        }
    }
}
