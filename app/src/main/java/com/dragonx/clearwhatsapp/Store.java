package com.dragonx.clearwhatsapp;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
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

    public List<File> getFiles(String dir) {
        return getFiles(dir, null);
    }

    public List<File> getFiles(String dir, final String[] matchRegex) {
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
        return files != null ? Arrays.asList(files) : null;
    }

    public void deleteFiles(String[] paths) {
        for (String path : paths) {
            File file = new File(path);
            file.delete();
        }
    }

    public String getReadableSize(File file) {
        long length = file.length();
        return SizeUnit.readableSizeUnit(length);
    }
}
