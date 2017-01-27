package com.latenightproductions.taggeroo.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemTapListener extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemTouchListener listener;
    private GestureDetector detector;

    public RecyclerItemTapListener(Context context, RecyclerItemTapListener.OnItemTouchListener listener) {
        this.listener = listener;
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && listener != null && detector.onTouchEvent(e)) {
            childView.setPressed(true);
            listener.onClick(view.getChildAdapterPosition(childView));
        }
        return false;
    }

    public interface OnItemTouchListener {
        void onClick(int position);
    }
}
