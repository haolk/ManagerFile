package com.dragonx.clearwhatsapp;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public final class ViewModel {
    public static Observable<PictureFolder> getAllPicture(Cursor cursor) {
        PictureFolder pictureFolder = new PictureFolder();
        List<PictureData> pictureList = new ArrayList<>();
        Long size = 0L;
        int total = 0;
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                int pictureDirIndex = cursor.getColumnIndex(Constant.PICTURE_DATA[0]);
                int pictureNameIndex = cursor.getColumnIndex(Constant.PICTURE_DATA[1]);
                int pictureSizeIndex = cursor.getColumnIndex(Constant.PICTURE_DATA[3]);
                String pictureDir = pictureDirIndex != -1 ? cursor.getString(pictureDirIndex) : null;
                String pictureName = pictureNameIndex != -1 ? cursor.getString(pictureNameIndex) : null;
                Long pictureSize = pictureSizeIndex != -1 ? cursor.getLong(pictureSizeIndex) : null;
                if (pictureDir != null) {
                    size += pictureSize;
                    total += 1;
                    PictureData pictureData = new PictureData(pictureDir, pictureName, pictureSize);
                    pictureList.add(pictureData);
                }
            } while (cursor.moveToNext());
        }
        pictureFolder.setPictureData(pictureList);
        pictureFolder.setSize(size);
        pictureFolder.setTotal(total);
        return Observable.just(pictureFolder);
    }
}
