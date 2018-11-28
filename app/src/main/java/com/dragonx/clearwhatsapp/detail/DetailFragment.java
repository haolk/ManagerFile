package com.dragonx.clearwhatsapp.detail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.dragonx.clearwhatsapp.Helper;
import com.dragonx.clearwhatsapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;

@EFragment(R.layout.fragment_detail)
public class DetailFragment extends Fragment implements FilesAdapter.OnFileItemListener {

    @FragmentArg
    ArrayList<FileItem> fileItems;

    @ViewById(R.id.rvData)
    RecyclerView rvData;
    @ViewById(R.id.tvNoData)
    TextView tvNoData;

    @AfterViews
    void afterViews() {
        if (fileItems != null && fileItems.size() > 0) {
            FilesAdapter filesAdapter = new FilesAdapter(getContext());
            filesAdapter.setFiles(fileItems);
            rvData.setLayoutManager(new LinearLayoutManager(getContext()));
            rvData.setAdapter(filesAdapter);
            filesAdapter.setListener(this);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            rvData.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(File file) {

    }
}
