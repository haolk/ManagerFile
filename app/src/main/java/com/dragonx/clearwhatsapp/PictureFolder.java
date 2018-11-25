package com.dragonx.clearwhatsapp;

import java.io.Serializable;
import java.util.List;

public class PictureFolder implements Serializable {
    private int total;
    private Long size;
    private List<PictureData> pictureData;

    public PictureFolder() {
    }

    public PictureFolder(int total, Long size, List<PictureData> pictureData) {
        this.total = total;
        this.size = size;
        this.pictureData = pictureData;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<PictureData> getPictureData() {
        return pictureData;
    }

    public void setPictureData(List<PictureData> pictureData) {
        this.pictureData = pictureData;
    }
}
