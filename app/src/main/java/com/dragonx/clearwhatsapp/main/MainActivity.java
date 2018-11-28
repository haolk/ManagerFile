package com.dragonx.clearwhatsapp.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragonx.clearwhatsapp.R;
import com.dragonx.clearwhatsapp.SizeUnit;
import com.dragonx.clearwhatsapp.Store;
import com.dragonx.clearwhatsapp.detail.DetailActivity_;
import com.dragonx.clearwhatsapp.detail.FileItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.tvSizeItemPhoto)
    TextView tvSizeItemPhoto;
    @ViewById(R.id.tvSizeItemVideo)
    TextView tvSizeItemVideo;
    @ViewById(R.id.tvSizeItemAudio)
    TextView tvSizeItemAudio;
    @ViewById(R.id.tvSizeItemFile)
    TextView tvSizeItemFile;
    @ViewById(R.id.tvSizeItemDatabase)
    TextView tvSizeItemDatabase;
    @ViewById(R.id.tvSelected)
    TextView tvSelected;
    @ViewById(R.id.tvSize)
    TextView tvSize;
    @ViewById(R.id.tvClearJunk)
    TextView tvClearJunk;

    @ViewById(R.id.ckbPhoto)
    CheckBox ckbPhoto;
    @ViewById(R.id.imgNext)
    ImageView imgNext;

    @ViewById(R.id.ckbVideo)
    CheckBox ckbVideo;
    @ViewById(R.id.imgNextVideo)
    ImageView imgNextVideo;

    @ViewById(R.id.ckbAudio)
    CheckBox ckbAudio;
    @ViewById(R.id.imgNextAudio)
    ImageView imgNextAudio;

    @ViewById(R.id.ckbFile)
    CheckBox ckbFile;
    @ViewById(R.id.imgNextFile)
    ImageView imgNextFile;

    @ViewById(R.id.ckbDatabase)
    CheckBox ckbDatabase;
    @ViewById(R.id.imgNextDatabase)
    ImageView imgNextDatabase;

    @ViewById(R.id.cslPhotoContainer)
    ConstraintLayout cslPhotoContainer;
    @ViewById(R.id.cslVideoContainer)
    ConstraintLayout cslVideoContainer;
    @ViewById(R.id.cslAudioContainer)
    ConstraintLayout cslAudioContainer;
    @ViewById(R.id.cslFileContainer)
    ConstraintLayout cslFileContainer;
    @ViewById(R.id.cslDatabaseContainer)
    ConstraintLayout cslDatabaseContainer;

    private static final int PERMISSION_CODE = 1001;
    private Store mStore;
    List<FileItem> imagesPrivate;
    List<FileItem> imagesSent;
    List<FileItem> videoPrivate;
    List<FileItem> videoSent;
    List<FileItem> audioPrivate;
    List<FileItem> audioSent;
    List<FileItem> filePrivate;
    List<FileItem> fileSent;
    List<FileItem> databases;

    private List<FileItem> fileItemsSeleted = new ArrayList<>();

    long imagesPrivateSize;
    long imagesSentSize;
    long videoPrivateSize;
    long videoSentSize;
    long audioPrivateSize;
    long audioSentSize;
    long filePrivateSize;
    long fileSentSize;
    long databasesSize;
    private long sizeSelected = 0L;

    @AfterViews
    void afterViews() {
//        mViewModel = new ViewModel();
        setSupportActionBar(toolbar);
        checkPermission();
        mStore = new Store(this);
        initData();
        initListener();
        tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
    }

    private void initData() {
        imagesPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Images/Private",
                new String[]{"png", "png", "jpg"});
        imagesPrivateSize = 0L;
        if (imagesPrivate != null && imagesPrivate.size() > 0)
            for (FileItem file : imagesPrivate) {
                imagesPrivateSize += file.getSize();
            }
        imagesSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Images/Sent",
                new String[]{"png", "png", "jpg"});
        imagesSentSize = 0L;
        if (imagesSent != null && imagesSent.size() > 0)
            for (FileItem file : imagesSent) {
                imagesSentSize += file.getSize();
            }
        tvSizeItemPhoto.setText(SizeUnit.readableSizeUnit((long) (imagesPrivateSize + imagesSentSize)));

        videoPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Video/Private",
                new String[]{"3gp", "mp4", "jpg"});
        videoPrivateSize = 0L;
        if (videoPrivate != null && videoPrivate.size() > 0)
            for (FileItem file : videoPrivate) {
                videoPrivateSize += file.getSize();
            }
        videoSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Video/Sent",
                new String[]{"3gp", "mp4", "jpg"});
        videoSentSize = 0L;
        if (videoSent != null && videoSent.size() > 0)
            for (FileItem file : videoSent) {
                videoSentSize += file.getSize();
            }
        tvSizeItemVideo.setText(SizeUnit.readableSizeUnit((long) (videoPrivateSize + videoSentSize)));

        audioPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Audio/Private",
                new String[]{"mp3", "flac", "m4a"});
        audioPrivateSize = 0L;
        if (audioPrivate != null && audioPrivate.size() > 0)
            for (FileItem file : audioPrivate) {
                audioPrivateSize += file.getSize();
            }
        audioSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Audio/Sent",
                new String[]{"mp3", "flac", "m4a"});
        audioSentSize = 0L;
        if (audioSent != null && audioSent.size() > 0)
            for (FileItem file : audioSent) {
                audioSentSize += file.getSize();
            }
        tvSizeItemAudio.setText(SizeUnit.readableSizeUnit((long) (audioPrivateSize + audioSentSize)));

        filePrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Documents/Private",
                new String[]{"doc", "docx", "pdf"});
        filePrivateSize = 0L;
        if (filePrivate != null && filePrivate.size() > 0)
            for (FileItem file : filePrivate) {
                filePrivateSize += file.getSize();
            }
        fileSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Documents/Sent",
                new String[]{"doc", "docx", "pdf"});
        fileSentSize = 0L;
        if (fileSent != null && fileSent.size() > 0)
            for (FileItem file : fileSent) {
                fileSentSize += file.getSize();
            }
        tvSizeItemFile.setText(SizeUnit.readableSizeUnit((long) (filePrivateSize + fileSentSize)));

        databases = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Databases");
        databasesSize = 0L;
        if (databases != null && databases.size() > 0)
            for (FileItem file : databases) {
                databasesSize += file.getSize();
            }
        tvSizeItemDatabase.setText(SizeUnit.readableSizeUnit((long) (databasesSize)));
    }

    private void initListener() {
        cslPhotoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity_.intent(MainActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) imagesPrivate)
                        .fileItemSent((ArrayList<FileItem>) imagesSent)
                        .start();
            }
        });
        cslVideoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity_.intent(MainActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) videoPrivate)
                        .fileItemSent((ArrayList<FileItem>) videoSent)
                        .start();
            }
        });
        cslAudioContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity_.intent(MainActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) audioPrivate)
                        .fileItemSent((ArrayList<FileItem>) audioSent)
                        .start();
            }
        });
        cslFileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity_.intent(MainActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) filePrivate)
                        .fileItemSent((ArrayList<FileItem>) fileSent)
                        .start();
            }
        });
        cslDatabaseContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity_.intent(MainActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) databases)
                        .fileItemSent(null)
                        .start();
            }
        });

        //===============================================//
        cslPhotoContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (ckbPhoto.getVisibility() == View.VISIBLE) {
                    ckbPhoto.setVisibility(View.GONE);
                    imgNext.setVisibility(View.VISIBLE);
                } else {
                    ckbPhoto.setVisibility(View.VISIBLE);
                    imgNext.setVisibility(View.GONE);
                }
                return true;
            }
        });
        ckbPhoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sizeSelected += imagesPrivateSize + imagesSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.addAll(imagesPrivate);
                    fileItemsSeleted.addAll(imagesSent);
                } else {
                    sizeSelected -= imagesPrivateSize + imagesSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.removeAll(imagesPrivate);
                    fileItemsSeleted.removeAll(imagesSent);
                }
            }
        });

        cslVideoContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (ckbVideo.getVisibility() == View.VISIBLE) {
                    ckbVideo.setVisibility(View.GONE);
                    imgNextVideo.setVisibility(View.VISIBLE);
                } else {
                    ckbVideo.setVisibility(View.VISIBLE);
                    imgNextVideo.setVisibility(View.GONE);
                }
                return true;
            }
        });
        ckbVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sizeSelected += videoPrivateSize + videoSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.addAll(videoPrivate);
                    fileItemsSeleted.addAll(videoSent);
                } else {
                    sizeSelected -= videoPrivateSize + videoSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.removeAll(videoPrivate);
                    fileItemsSeleted.removeAll(videoSent);
                }
            }
        });

        cslAudioContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (ckbAudio.getVisibility() == View.VISIBLE) {
                    ckbAudio.setVisibility(View.GONE);
                    imgNextAudio.setVisibility(View.VISIBLE);
                } else {
                    ckbAudio.setVisibility(View.VISIBLE);
                    imgNextAudio.setVisibility(View.GONE);
                }
                return true;
            }
        });
        ckbAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sizeSelected += audioPrivateSize + audioSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.addAll(audioPrivate);
                    fileItemsSeleted.addAll(audioSent);
                } else {
                    sizeSelected -= audioPrivateSize + audioSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.removeAll(audioPrivate);
                    fileItemsSeleted.removeAll(audioSent);
                }
            }
        });

        cslFileContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (ckbFile.getVisibility() == View.VISIBLE) {
                    ckbFile.setVisibility(View.GONE);
                    imgNextFile.setVisibility(View.VISIBLE);
                } else {
                    ckbFile.setVisibility(View.VISIBLE);
                    imgNextFile.setVisibility(View.GONE);
                }
                return true;
            }
        });
        ckbFile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sizeSelected += filePrivateSize + fileSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.addAll(filePrivate);
                    fileItemsSeleted.addAll(fileSent);
                } else {
                    sizeSelected -= filePrivateSize + fileSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.removeAll(filePrivate);
                    fileItemsSeleted.removeAll(fileSent);
                }
            }
        });

        cslDatabaseContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (ckbDatabase.getVisibility() == View.VISIBLE) {
                    ckbDatabase.setVisibility(View.GONE);
                    imgNextDatabase.setVisibility(View.VISIBLE);
                } else {
                    ckbDatabase.setVisibility(View.VISIBLE);
                    imgNextDatabase.setVisibility(View.GONE);
                }
                return true;
            }
        });
        ckbDatabase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sizeSelected += databasesSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.addAll(databases);
                } else {
                    sizeSelected -= databasesSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSeleted.removeAll(databases);
                }
            }
        });

        tvClearJunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStore.deleteFiles(fileItemsSeleted);
                initData();
            }
        });
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
}
