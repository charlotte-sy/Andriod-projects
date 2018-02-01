package com.app.examplefour.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerGridItemDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public RecyclerGridItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public RecyclerGridItemDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
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

        // Positions between first and last
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);

        // First position
        if (itemPosition == 0 || itemPosition == 1) {
            outRect.set(mItemOffset, mItemOffset * 2, mItemOffset, mItemOffset);
        }

        if ((itemCount & 1) == 0) {
            if (itemCount > 0 && itemPosition == itemCount - 2) {
                outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset * 2);
            }

            if (itemCount > 0 && itemPosition == itemCount - 1) {
                outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset * 2);
            }
        } else {
            if (itemCount > 0 && itemPosition == itemCount - 1) {
                outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset * 2);
            }
        }
    }
}
