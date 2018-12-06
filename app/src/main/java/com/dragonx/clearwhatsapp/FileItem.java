package com.dragonx.clearwhatsapp;

import java.io.Serializable;

/**
 * @author HaoLeK
 * Created on 28/11/2018
 */
public class FileItem implements Serializable {
    private String path;
    private String name;
    private long size;

    public FileItem(String path,String name, long size) {
        this.path = path;
        this.name = name;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
