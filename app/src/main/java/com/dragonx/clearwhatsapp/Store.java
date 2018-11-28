package com.dragonx.clearwhatsapp;

import android.content.Context;

import com.dragonx.clearwhatsapp.detail.FileItem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoLeK
 * Created on 27/11/2018
 */
public class Store {

    private Context context;

    public Store(Context context) {
        this.context = context;
    }

    public List<FileItem> getFiles(String dir) {
        return getFiles(dir, null);
    }

    public List<FileItem> getFiles(String dir, final String[] matchRegex) {
        File file = new File(dir);
        File[] files;
        if (matchRegex != null) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    for (String regex : matchRegex) {
                        if (name.endsWith("." + regex)) {
                            return true;
                        }
                    }
                    return false;
                }
            };
            files = file.listFiles(filter);
        } else {
            files = file.listFiles();
        }
        List<FileItem> fileItems = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (File file1 : files) {
                fileItems.add(new FileItem(file1.getAbsolutePath(), file1.getName(), file1.length()));
            }
        }
        return fileItems;
    }

    public void deleteFiles(List<FileItem> paths) {
        for (FileItem path : paths) {
            File file = new File(path.getPath());
            file.delete();
        }
    }

    public String getReadableSize(File file) {
        long length = file.length();
        return SizeUnit.readableSizeUnit(length);
    }

    public double getSize(File file, SizeUnit unit) {
        long length = file.length();
        return (double) length / (double) unit.inBytes();
    }
}
