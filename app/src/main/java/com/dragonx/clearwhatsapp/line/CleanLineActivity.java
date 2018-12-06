package com.dragonx.clearwhatsapp.line;

import android.Manifest;
import android.annotation.SuppressLint;
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

import com.dragonx.clearwhatsapp.FileItem;
import com.dragonx.clearwhatsapp.R;
import com.dragonx.clearwhatsapp.SizeUnit;
import com.dragonx.clearwhatsapp.Store;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
@EActivity(R.layout.activity_clean_line)
public class CleanLineActivity extends AppCompatActivity {

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

    @ViewById(R.id.ckbAudio)
    CheckBox ckbAudio;
    @ViewById(R.id.imgNextAudio)
    ImageView imgNextAudio;

    @ViewById(R.id.ckbFile)
    CheckBox ckbFile;
    @ViewById(R.id.imgNextFile)
    ImageView imgNextFile;


    @ViewById(R.id.cslPhotoContainer)
    ConstraintLayout cslPhotoContainer;
    @ViewById(R.id.cslAudioContainer)
    ConstraintLayout cslAudioContainer;
    @ViewById(R.id.cslFileContainer)
    ConstraintLayout cslFileContainer;

    private static final int PERMISSION_CODE = 1001;
    private Store mStore;
    List<FileItem> photos;
    List<FileItem> audios;
    List<FileItem> files;

    private List<FileItem> fileItemsSelected = new ArrayList<>();

    private long photoSize;
    private long audioSize;
    private long fileSize;
    private long sizeSelected = 0L;
    private long total = 0L;

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.clean_line);
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
        photos = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/jp.naver.line.android/storage/mo/u546f095eab66ea6787df7e80881d1cc9");
        photos.addAll(mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/jp.naver.line.android/storage/mo/u55206159246c6f8597156f327f81af8c"));
        photoSize = 0L;
        if (photos != null && photos.size() > 0)
            for (FileItem file : photos) {
                photoSize += file.getSize();
            }
        tvSizeItemPhoto.setText(SizeUnit.readableSizeUnit(photoSize));

        audios = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/jp.naver.line.android/storage/mo/u546f095eab66ea6787df7e80881d1cc9/f");
        audios.addAll(mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/jp.naver.line.android/storage/mo/u55206159246c6f8597156f327f81af8c/f"));
        audioSize = 0L;
        if (audios != null && audios.size() > 0)
            for (FileItem file : audios) {
                audioSize += file.getSize();
            }
        tvSizeItemAudio.setText(SizeUnit.readableSizeUnit(audioSize));

        files = mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/jp.naver.line.android/storage/mo/u546f095eab66ea6787df7e80881d1cc9/f",
                new String[]{"doc", "docx", "pdf", "txt"});
        files.addAll(mStore.getFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/jp.naver.line.android/storage/mo/u55206159246c6f8597156f327f81af8c/f",
                new String[]{"doc", "docx", "pdf", "txt"}));
        fileSize = 0L;
        if (files != null && files.size() > 0)
            for (FileItem file : files) {
                fileSize += file.getSize();
            }
        tvSizeItemFile.setText(SizeUnit.readableSizeUnit(fileSize));
        total = photoSize + audioSize + fileSize;
        tvSize.setText(SizeUnit.readableSize(total));
        tbType.setText(SizeUnit.readableUnit(total));
        tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
    }

    private void resetCheckBook() {
        ckbPhoto.setVisibility(View.GONE);
        ckbPhoto.setChecked(false);
        imgNext.setVisibility(View.VISIBLE);
        ckbAudio.setVisibility(View.GONE);
        ckbAudio.setChecked(false);
        imgNextAudio.setVisibility(View.VISIBLE);
        ckbFile.setVisibility(View.GONE);
        ckbFile.setChecked(false);
        imgNextFile.setVisibility(View.VISIBLE);
    }

    private void initListener() {
        cslPhotoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailLineActivity_.intent(CleanLineActivity.this)
                        .fileItems((ArrayList<FileItem>) photos)
                        .isPhoto(true)
                        .isVideo(false)
                        .startForResult(1001);
            }
        });
        cslAudioContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailLineActivity_.intent(CleanLineActivity.this)
                        .fileItems((ArrayList<FileItem>) audios)
                        .isPhoto(false)
                        .isVideo(false)
                        .startForResult(1001);
            }
        });
        cslFileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailLineActivity_.intent(CleanLineActivity.this)
                        .fileItems((ArrayList<FileItem>) files)
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
                    sizeSelected += photoSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.addAll(photos);
                } else {
                    sizeSelected -= photoSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.removeAll(photos);
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
                    sizeSelected += audioSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.addAll(audios);
                } else {
                    sizeSelected -= audioSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.removeAll(audios);
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
                    sizeSelected += fileSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.addAll(files);
                } else {
                    sizeSelected -= fileSize;
                    tvSelected.setText(getString(R.string.selected_s, SizeUnit.readableSizeUnit(sizeSelected)));
                    fileItemsSelected.removeAll(files);
                }
            }
        });

        tvClearJunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeSelected = 0L;
                final ProgressDialog dialog = ProgressDialog.show(CleanLineActivity.this, "", "Deleting...",
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
        if (requestCode == 1001 & resultCode == 1001) {
            initData();
        }
    }
}
