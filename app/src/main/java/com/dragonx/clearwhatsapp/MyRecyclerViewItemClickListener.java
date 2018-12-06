package com.dragonx.clearwhatsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author HaoLeK
 * Created on 30/11/2018
 */
public class MyRecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null) {
                onItemClickListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
            }
        }
    });

    public MyRecyclerViewItemClickListener(Context context, final OnItemClickListener onItemClickListener, final RecyclerView recyclerView) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {

        View childView = view.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        if (childView != null && gestureDetector.onTouchEvent(motionEvent)) {
            this.onItemClickListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
