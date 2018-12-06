package com.dragonx.clearwhatsapp.whatsapp.detail;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dragonx.clearwhatsapp.FileItem;
import com.dragonx.clearwhatsapp.FilesAdapter;
import com.dragonx.clearwhatsapp.MyRecyclerViewItemClickListener;
import com.dragonx.clearwhatsapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_detail)
public class DetailFragment extends Fragment implements MyRecyclerViewItemClickListener.OnItemClickListener {

    @FragmentArg
    ArrayList<FileItem> fileItems;
    @FragmentArg
    Boolean isPhoto;
    @FragmentArg
    Boolean isVideo;

    @ViewById(R.id.rvData)
    RecyclerView rvData;
    @ViewById(R.id.tvNoData)
    TextView tvNoData;

    private List<FileItem> fileSelected = new ArrayList<>();
    private FilesAdapter filesAdapter;
    private Boolean isMultiSelect = false;

    @AfterViews
    void afterViews() {
        isMultiSelect = false;
        int photoSize = getResources().getDimensionPixelSize(R.dimen.item_gallery_photo_size);
        int gridMargin = getResources().getDimensionPixelSize(R.dimen.item_gallery_margin);
        int spanCount = getResources().getDisplayMetrics().widthPixels / (photoSize + 2 * gridMargin);

        if (fileItems != null && fileItems.size() > 0) {
            filesAdapter = new FilesAdapter(getContext(), isPhoto, isVideo);
            if (isPhoto || isVideo) {
                rvData.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), spanCount, GridLayoutManager.VERTICAL, false));
                rvData.getRecycledViewPool().setMaxRecycledViews(0, spanCount * (getResources().getDisplayMetrics().heightPixels / photoSize) * 2);
            } else {
                rvData.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            filesAdapter.setFiles(fileItems);
            rvData.setAdapter(filesAdapter);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            rvData.setVisibility(View.GONE);
        }
        initListener();

    }

    private void initListener() {
        rvData.addOnItemTouchListener(new MyRecyclerViewItemClickListener(getContext(), this, rvData));
    }

    @Override
    public void onItemClick(View view, int position) {
        if (isMultiSelect) {
            multiSelect(position);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (!isMultiSelect) {
            isMultiSelect = true;
            fileSelected.clear();
        }
        multiSelect(position);
    }

    private void multiSelect(int position) {
        FileItem fileItem = fileItems.get(position);
        if (fileSelected.contains(fileItem)) {
            fileSelected.remove(fileItem);
            ((DetailWhatsAppActivity) getActivity()).removeFile(fileItem);
        } else {
            fileSelected.add(fileItem);
            ((DetailWhatsAppActivity) getActivity()).addFile(fileItem);
        }
        filesAdapter.setSelectedFile(fileSelected);
        isMultiSelect = fileSelected.size() > 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isMultiSelect = false;
    }
}
