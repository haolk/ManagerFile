package com.dragonx.clearwhatsapp.whatsapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.dragonx.clearwhatsapp.whatsapp.detail.DetailWhatsAppActivity_;
import com.dragonx.clearwhatsapp.FileItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_clean_whats_app)
public class CleanWhatsAppActivity extends AppCompatActivity {

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
    @ViewById(R.id.tbType)
    TextView tbType;

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

    private List<FileItem> fileItemsSelected = new ArrayList<>();

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
    private long total = 0L;

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_clean_whats_app);
        checkPermission();
        mStore = new Store(this);
        initData();
        initListener();
    }

    @OptionsItem(android.R.id.home)
    void clickHome() {
        onBackPressed();
    }

    private void initData() {
        imagesPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Images",
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
        tvSizeItemPhoto.setText(SizeUnit.readableSizeUnit(imagesPrivateSize + imagesSentSize));

        videoPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Video",
                new String[]{"mp4"});
        videoPrivateSize = 0L;
        if (videoPrivate != null && videoPrivate.size() > 0)
            for (FileItem file : videoPrivate) {
                videoPrivateSize += file.getSize();
            }
        videoSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Video/Sent",
                new String[]{"mp4"});
        videoSentSize = 0L;
        if (videoSent != null && videoSent.size() > 0)
            for (FileItem file : videoSent) {
                videoSentSize += file.getSize();
            }
        tvSizeItemVideo.setText(SizeUnit.readableSizeUnit(videoPrivateSize + videoSentSize));

        audioPrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Audio",
                new String[]{"mp3", "flac", "m4a"});
        audioPrivateSize = 0L;
        if (audioPrivate != null && audioPrivate.size() > 0)
            for (FileItem file : audioPrivate) {
                audioPrivateSize += file.getSize();
            }
        audioSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Audio/Sent",
                new String[]{"mp3", "flac", "m4a"});
        audioSentSize = 0L;
        if (audioSent != null && audioSent.size() > 0)
            for (FileItem file : audioSent) {
                audioSentSize += file.getSize();
            }
        tvSizeItemAudio.setText(SizeUnit.readableSizeUnit(audioPrivateSize + audioSentSize));

        filePrivate = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Documents",
                new String[]{"doc", "docx", "pdf", "txt"});
        filePrivateSize = 0L;
        if (filePrivate != null && filePrivate.size() > 0)
            for (FileItem file : filePrivate) {
                filePrivateSize += file.getSize();
            }
        fileSent = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Documents/Sent",
                new String[]{"doc", "docx", "pdf", "txt"});
        fileSentSize = 0L;
        if (fileSent != null && fileSent.size() > 0)
            for (FileItem file : fileSent) {
                fileSentSize += file.getSize();
            }
        tvSizeItemFile.setText(SizeUnit.readableSizeUnit(filePrivateSize + fileSentSize));

        databases = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Databases");
        databasesSize = 0L;
        if (databases != null && databases.size() > 0)
            for (FileItem file : databases) {
                databasesSize += file.getSize();
            }
        tvSizeItemDatabase.setText(SizeUnit.readableSizeUnit(databasesSize));
        total = imagesPrivateSize + imagesSentSize + videoPrivateSize + videoSentSize + audioPrivateSize + audioSentSize + filePrivateSize + fileSentSize + databasesSize;
        tvSize.setText(SizeUnit.readableSize(total));
        tbType.setText(SizeUnit.readableUnit(total));
        tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
    }

    private void resetCheckBook() {
        ckbPhoto.setVisibility(View.GONE);
        ckbPhoto.setChecked(false);
        imgNext.setVisibility(View.VISIBLE);
        ckbVideo.setVisibility(View.GONE);
        ckbVideo.setChecked(false);
        imgNextVideo.setVisibility(View.VISIBLE);
        ckbAudio.setVisibility(View.GONE);
        ckbAudio.setChecked(false);
        imgNextAudio.setVisibility(View.VISIBLE);
        ckbFile.setVisibility(View.GONE);
        ckbFile.setChecked(false);
        imgNextFile.setVisibility(View.VISIBLE);
        ckbDatabase.setVisibility(View.GONE);
        ckbDatabase.setChecked(false);
        imgNextDatabase.setVisibility(View.VISIBLE);
    }

    private void initListener() {
        cslPhotoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailWhatsAppActivity_.intent(CleanWhatsAppActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) imagesPrivate)
                        .fileItemSent((ArrayList<FileItem>) imagesSent)
                        .isPhoto(true)
                        .isVideo(false)
                        .startForResult(1001);
            }
        });
        cslVideoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailWhatsAppActivity_.intent(CleanWhatsAppActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) videoPrivate)
                        .fileItemSent((ArrayList<FileItem>) videoSent)
                        .isPhoto(false)
                        .isVideo(true)
                        .startForResult(1001);
            }
        });
        cslAudioContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailWhatsAppActivity_.intent(CleanWhatsAppActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) audioPrivate)
                        .fileItemSent((ArrayList<FileItem>) audioSent)
                        .isPhoto(false)
                        .isVideo(false)
                        .startForResult(1001);
            }
        });
        cslFileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailWhatsAppActivity_.intent(CleanWhatsAppActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) filePrivate)
                        .fileItemSent((ArrayList<FileItem>) fileSent)
                        .isPhoto(false)
                        .isVideo(false)
                        .startForResult(1001);
            }
        });
        cslDatabaseContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailWhatsAppActivity_.intent(CleanWhatsAppActivity.this)
                        .fileItemReceiver((ArrayList<FileItem>) databases)
                        .fileItemSent(null)
                        .isPhoto(false)
                        .isVideo(false)
                        .startForResult(1001);
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
                    fileItemsSelected.addAll(imagesPrivate);
                    fileItemsSelected.addAll(imagesSent);
                } else {
                    sizeSelected -= imagesPrivateSize + imagesSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.removeAll(imagesPrivate);
                    fileItemsSelected.removeAll(imagesSent);
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
                    fileItemsSelected.addAll(videoPrivate);
                    fileItemsSelected.addAll(videoSent);
                } else {
                    sizeSelected -= videoPrivateSize + videoSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.removeAll(videoPrivate);
                    fileItemsSelected.removeAll(videoSent);
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
                    fileItemsSelected.addAll(audioPrivate);
                    fileItemsSelected.addAll(audioSent);
                } else {
                    sizeSelected -= audioPrivateSize + audioSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.removeAll(audioPrivate);
                    fileItemsSelected.removeAll(audioSent);
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
                    fileItemsSelected.addAll(filePrivate);
                    fileItemsSelected.addAll(fileSent);
                } else {
                    sizeSelected -= filePrivateSize + fileSentSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.removeAll(filePrivate);
                    fileItemsSelected.removeAll(fileSent);
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
                    fileItemsSelected.addAll(databases);
                } else {
                    sizeSelected -= databasesSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.removeAll(databases);
                }
            }
        });

        tvClearJunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeSelected = 0L;
                final ProgressDialog dialog = ProgressDialog.show(CleanWhatsAppActivity.this, "", "Deleting...",
                        true);
                dialog.show();
                if (fileItemsSelected != null && fileItemsSelected.size() > 0) {
                    mStore.deleteFiles(fileItemsSelected);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        initData();
                        resetCheckBook();
                    }
                }, 1000);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 & resultCode == 10001) {
            initData();
        }
    }
}
