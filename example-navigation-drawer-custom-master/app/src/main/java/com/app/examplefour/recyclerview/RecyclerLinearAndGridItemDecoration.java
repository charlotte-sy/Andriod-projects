package com.app.examplefour.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerLinearAndGridItemDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public RecyclerLinearAndGridItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public RecyclerLinearAndGridItemDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
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

        if (itemPosition == 0) {
            outRect.set(mItemOffset, mItemOffset * 2, mItemOffset, mItemOffset);
        } else {
            if (itemPosition == itemCount - 2 && (itemPosition + 1) % 3 != 0) {
                if (itemPosition % 3 != 0)
                    outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset * 2);
            } else if (itemPosition == itemCount - 1 && (itemPosition + 1) % 3 != 0) {
                outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset * 2);
            } else if (itemPosition == itemCount - 1 && (itemPosition + 1) % 3 == 0) {
                outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset * 2);
            } else {
                outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
            }
        }
    }
}