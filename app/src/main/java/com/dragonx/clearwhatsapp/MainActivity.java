package com.dragonx.clearwhatsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    private static final int PERMISSION_CODE = 1001;
    private static final int LOADER_PICTURE = 1002;
    private ViewModel mViewModel;
    private Store mStore;

    @AfterViews
    void afterViews() {
//        mViewModel = new ViewModel();
        setSupportActionBar(toolbar);
        checkPermission();
        mStore = new Store(this);
        initData();
    }

    private void initData() {
//        MainActivity.this.getSupportLoaderManager().initLoader(LOADER_PICTURE, null, this);
//        MainActivity.this.getSupportLoaderManager().initLoader(LOADER_PICTURE, null, this);
        List<File> imagesPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Images/Private",
                new String[]{"png", "png", "jpg"});
        List<File> imagesSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Images/Sent",
                new String[]{"png", "png", "jpg"});

        List<File> videoPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Video/Private",
                new String[]{"3gp", "mp4", "jpg"});
        List<File> videoSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Video/Sent",
                new String[]{"3gp", "mp4", "jpg"});

        List<File> audioPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Audio/Private",
                new String[]{"mp3", "flac", "m4a"});
        List<File> audioSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Audio/Sent",
                new String[]{"mp3", "flac", "m4a"});


        List<File> databases = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Databases");
        Log.e("HAOHAO", "/WhatsApp/Media/WhatsApp Images/Private: " + imagesPrivate.size());
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
            finish();
        }
    }

//    @NonNull
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
//        return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Constant.PICTURE_DATA,
//                Constant.PICTURE_WHERE, Constant.PICTURE_TYPE, Constant.PICTURE_DATA[2] + " DESC");
//    }
//
//    @SuppressLint("CheckResult")
//    @Override
//    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
//        ViewModel.getAllPicture(cursor)
//                .observeOn(Schedulers.io())
//                .subscribe(handleGetPictureSuccess);
//        removeLoader();
//    }
//
//    Observer<PictureFolder> handleGetPictureSuccess = new Observer<PictureFolder>() {
//
//        @Override
//        public void onSubscribe(Disposable d) {
//        }
//
//        @Override
//        public void onNext(PictureFolder pictureFolder) {
//            // TODO
//            Log.e("HAOHAO", "haha " + pictureFolder.getSize());
//        }
//
//        @Override
//        public void onError(Throwable e) {
//
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    };
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
//    }
//
//    private void removeLoader() {
//        if (getSupportLoaderManager().hasRunningLoaders()) {
//            getSupportLoaderManager().destroyLoader(LOADER_PICTURE);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        removeLoader();
//    }

    /**
     * Func format size
     */
    private String formatSize(Long size) {
        if (size <= 0)
            return "0";
        String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024.0));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups)) + " " + units[digitGroups];
    }
}
