package com.example.nik.flickrapidemo.Activity;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nik.flickrapidemo.ImageHandler.ImageResponseCallback;
import com.example.nik.flickrapidemo.ImageHandler.ImageUtils;
import com.example.nik.flickrapidemo.R;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> imagesList;
    private ImageUtils imageUtils;
    private RecyclerViewCallbackInterface callbackInterface;

    private int LOAD_MORE_THRESHOLD = 15;

    public ImagesAdapter(List<String> imagesList, ImageUtils imageUtils, RecyclerViewCallbackInterface callbackInterface) {
        this.imagesList = imagesList;
        this.imageUtils = imageUtils;
        this.callbackInterface = callbackInterface;
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
        vh.imageView.setImageBitmap(null);
        String url = imagesList.get(position);
        imageUtils.getImage(url, vh.imageView, new ImageResponseCallback() {
            @Override
            public void onBitmapReceived(Bitmap bitmap) {
                if (bitmap != null) {
                    vh.imageView.setImageBitmap(bitmap);
                }
            }
        });
        if (position + LOAD_MORE_THRESHOLD > imagesList.size()) {
            callbackInterface.loadMore();
        }
    }

    @Override
    public int getItemCount() {
        return imagesList != null ? imagesList.size() : 0;
    }

    public void addItemsToList(List<String> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        if (imagesList != null) {
            int positionStart = imagesList.size();
            imagesList.addAll(list);
            notifyItemRangeInserted(positionStart, list.size());
        } else {
            imagesList = new ArrayList<>();
            imagesList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void resetList() {
        if (imagesList != null) {
            imagesList.clear();
        } else {
            imagesList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
