package com.example.nik.flickrapidemo.data;

import java.util.ArrayList;
import java.util.List;

public class ImageResponseDto {
    private List<String> imageList;

    public ImageResponseDto(List<String> imageList) {
        setImageList(imageList);
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void addItemsToList(List<String> imageItemsList) {
        if (imageList == null) {
            setImageList(new ArrayList<String>());
        }
        imageList.addAll(imageItemsList);
    }

    private void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
