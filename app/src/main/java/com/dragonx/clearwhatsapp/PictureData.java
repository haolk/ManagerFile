package com.dragonx.clearwhatsapp;

import java.io.Serializable;

public class PictureData implements Serializable {
    private String name;
    private String dir;
    private Long size;

    public PictureData() {
    }

    public PictureData(String name, String dir, Long size) {
        this.name = name;
        this.dir = dir;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
