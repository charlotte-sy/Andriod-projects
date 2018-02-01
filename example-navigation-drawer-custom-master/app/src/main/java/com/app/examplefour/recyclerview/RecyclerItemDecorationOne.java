package com.app.examplefour.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerItemDecorationOne extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public RecyclerItemDecorationOne(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public RecyclerItemDecorationOne(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }

        final int itemCount = state.getItemCount();

        if (itemPosition == 0) {    // First position
            outRect.set(0, 0, 0, mItemOffset);
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {    // Last position
            outRect.set(0, 0, 0, 0);
        } else {    // Positions between first and last
            outRect.set(0, 0, 0, mItemOffset);
        }
    }
}