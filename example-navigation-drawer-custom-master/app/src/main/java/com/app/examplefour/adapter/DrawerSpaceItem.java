package com.app.examplefour.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.examplefour.R;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.model.AbstractDrawerItem;

public class DrawerSpaceItem extends AbstractDrawerItem<DrawerSpaceItem, DrawerSpaceItem.ViewHolder> {

    public Integer height = null;

    @Override
    public int getType() {
        return R.id.adapter_drawer_space_id;
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return R.layout.adapter_drawer_space_item;
    }

    public DrawerSpaceItem withHeightDp(Integer height) {
        this.height = height;
        return this;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        //set the identifier from the drawerItem here. It can be used to run tests
        viewHolder.itemView.setId(hashCode());

        //define how the divider should look like
        viewHolder.view.setClickable(false);
        viewHolder.view.setEnabled(false);

        if (height != null) {
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, viewHolder.view.getResources().getDisplayMetrics());

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewHolder.space.getLayoutParams();
            params.setMargins(0, px, 0, 0);
            viewHolder.space.setLayoutParams(params);
        }

        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, viewHolder.itemView);
    }

    @Override
    public ViewHolderFactory<ViewHolder> getFactory() {
        return new ItemFactory();
    }

    public static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView space;

        private ViewHolder(View view) {
            super(view);
            this.view = view;
            this.space = (TextView) view.findViewById(R.id.item_text);
        }
    }
}