package com.dragonx.clearwhatsapp;

import android.provider.MediaStore;

public final class Constant {
    public static final String[] PICTURE_DATA = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.SIZE};
    public static final String[] PICTURE_TYPE = new String[]{"image/jpeg", "image/png", "image/jpg","0"};
    public static final String PICTURE_WHERE = "(" + MediaStore.Images.Media.MIME_TYPE + "=? or " +
            MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?) and " +
            MediaStore.Images.Media.SIZE + ">?";

}
