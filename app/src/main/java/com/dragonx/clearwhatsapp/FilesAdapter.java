package com.dragonx.clearwhatsapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create @HaoLeK
 */
public class FilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FileItem> mFiles;
    private List<FileItem> mSelectedFile = new ArrayList<>();
    private boolean isPhoto;
    private boolean isVideo;
    private Context context;

    public FilesAdapter(Context context, Boolean isPhoto, Boolean isVideo) {
        this.context = context;
        this.isPhoto = isPhoto;
        this.isVideo = isVideo;
    }

    public void setSelectedFile(List<FileItem> selectedFile) {
        this.mSelectedFile = selectedFile;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FileItem file = mFiles.get(position);
        FileViewHolder fileViewHolder = (FileViewHolder) holder;
        if (mSelectedFile.size() > 0) {
            setViewIndicatingChoiceMode(fileViewHolder);
            fileViewHolder.rbSelected.setChecked(mSelectedFile.contains(mFiles.get(position)));
        } else {
            setViewIndicatingNormalMode(fileViewHolder);
        }
        if (isPhoto && !isVideo) {
            fileViewHolder.mLlContainer.setVisibility(View.GONE);
            fileViewHolder.rlContainerImage.setVisibility(View.VISIBLE);
            fileViewHolder.viewLine.setVisibility(View.GONE);
            fileViewHolder.rlPlay.setVisibility(View.GONE);
            Uri uri = Uri.fromFile(new File(file.getPath()));
            Glide.with(context).load(uri).into(fileViewHolder.mImgPhoto);
        } else if (!isPhoto && isVideo) {
            fileViewHolder.mLlContainer.setVisibility(View.GONE);
            fileViewHolder.rlContainerImage.setVisibility(View.VISIBLE);
            fileViewHolder.viewLine.setVisibility(View.GONE);
            fileViewHolder.rlPlay.setVisibility(View.VISIBLE);
            Uri uri = Uri.fromFile(new File(file.getPath()));
            Glide.with(context).load(uri).into(fileViewHolder.mImgPhoto);
        } else {
            fileViewHolder.mLlContainer.setVisibility(View.VISIBLE);
            fileViewHolder.rlPlay.setVisibility(View.GONE);
            fileViewHolder.rlContainerImage.setVisibility(View.GONE);
            fileViewHolder.viewLine.setVisibility(View.VISIBLE);
            fileViewHolder.mName.setText(file.getName());
            fileViewHolder.mIcon.setImageResource(R.drawable.ic_file_primary_24dp);
            fileViewHolder.mSize.setVisibility(View.VISIBLE);
            fileViewHolder.mSize.setText(SizeUnit.readableSizeUnit(file.getSize()));
        }

    }

    @Override
    public int getItemCount() {
        return mFiles != null ? mFiles.size() : 0;
    }

    public void setFiles(List<FileItem> files) {
        this.mFiles = files;
        notifyDataSetChanged();
    }

    private void setViewIndicatingChoiceMode(FileViewHolder holder) {
        holder.rbSelected.setVisibility(View.VISIBLE);
    }

    private void setViewIndicatingNormalMode(FileViewHolder holder) {
        holder.rbSelected.setVisibility(View.GONE);
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mSize;
        ImageView mIcon;
        ImageView mImgPhoto;
        RadioButton rbSelected;
        LinearLayout mLlContainer;
        RelativeLayout rlContainerImage;
        View viewLine;
        RelativeLayout rlPlay;

        FileViewHolder(View v) {
            super(v);
            mName = v.findViewById(R.id.name);
            mSize = v.findViewById(R.id.size);
            mIcon = v.findViewById(R.id.icon);
            mImgPhoto = v.findViewById(R.id.imgView);
            mLlContainer = v.findViewById(R.id.llContainer);
            rlContainerImage = v.findViewById(R.id.rlContainerImage);
            rbSelected = v.findViewById(R.id.rbSelected);
            viewLine = v.findViewById(R.id.viewLine);
            rlPlay = v.findViewById(R.id.rlPlay);
        }
    }
}
