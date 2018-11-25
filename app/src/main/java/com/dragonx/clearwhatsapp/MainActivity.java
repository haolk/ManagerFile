package com.dragonx.clearwhatsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.security.Permission;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    private static final int PERMISSION_CODE = 1001;
    private static final int LOADER_PICTURE = 1002;
    private ViewModel mViewModel;

    @AfterViews
    void afterViews() {
        mViewModel = new ViewModel();
        setSupportActionBar(toolbar);
        checkPermission();
    }

    private void initData() {
//        MainActivity.this.getSupportLoaderManager().initLoader(LOADER_PICTURE, null, this);
        MainActivity.this.getSupportLoaderManager().initLoader(LOADER_PICTURE, null, this);
    }

    private void checkPermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
            } else {
                initData();
            }
        } else {
            initData();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
            finish();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Constant.PICTURE_DATA,
                Constant.PICTURE_WHERE, Constant.PICTURE_TYPE, Constant.PICTURE_DATA[2] + " DESC");
    }

    @SuppressLint("CheckResult")
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        ViewModel.getAllPicture(cursor)
                .observeOn(Schedulers.io())
                .subscribe(handleGetPictureSuccess);
        removeLoader();
    }

    Observer<PictureFolder> handleGetPictureSuccess = new Observer<PictureFolder>() {

        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(PictureFolder pictureFolder) {
            // TODO
            Log.e("HAOHAO", "haha " + pictureFolder.getSize());
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    private void removeLoader() {
        if (getSupportLoaderManager().hasRunningLoaders()) {
            getSupportLoaderManager().destroyLoader(LOADER_PICTURE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeLoader();
    }
}
