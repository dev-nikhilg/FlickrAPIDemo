package com.example.nik.flickrapidemo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nik.flickrapidemo.ImageHandler.ImageUtils;
import com.example.nik.flickrapidemo.R;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> imagesList;
    private ImageUtils imageUtils;

    public ImagesAdapter(List<String> imagesList, ImageUtils imageUtils) {
        this.imagesList = imagesList;
        this.imageUtils = imageUtils;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder vh = (ImageViewHolder) holder;
        imageUtils.getImage(imagesList.get(position), vh.imageView);
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

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}