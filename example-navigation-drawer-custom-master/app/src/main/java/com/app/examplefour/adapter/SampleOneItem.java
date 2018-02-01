package com.app.examplefour.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.examplefour.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.mikepenz.fastadapter.items.AbstractItem;

public class SampleOneItem extends AbstractItem<SampleOneItem, SampleOneItem.ViewHolder> {
    public String name = null;
    public String url = null;
    public String position = null;
    public int alpha = 100;

    //The unique ID for this type of item
    @Override
    public int getType() {
        return R.id.adapter_sample_item_id;
    }

    //The layout to be used for this type of item
    @Override
    public int getLayoutRes() {
        return R.layout.adapter_sample_item;
    }

    public SampleOneItem withName(String name) {
        this.name = name;
        return this;
    }

    public SampleOneItem withUrl(String url) {
        this.url = url;
        return this;
    }

    public SampleOneItem withPosition(String position) {
        this.position = position;
        return this;
    }

    //The logic to bind your data to the view
    @Override
    public void bindView(final ViewHolder viewHolder) {
        super.bindView(viewHolder);

        viewHolder.imageColor.getBackground().setAlpha(alpha);

        if (name != null)
            viewHolder.name.setText(name);

        if (position != null)
            viewHolder.position.setText(position);

        if (url != null)
            Glide.with(viewHolder.itemView.getContext()).load(url).placeholder(R.color.colorAccent).into(new GlideDrawableImageViewTarget(viewHolder.image) {
                @Override
                public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                    super.onResourceReady(drawable, anim);

                    if (viewHolder.progressBar != null)
                        viewHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);

                    if (viewHolder.progressBar != null)
                        viewHolder.progressBar.setVisibility(View.GONE);
                }
            });

    }

    //The viewHolder used for this item. This viewHolder is always reused by the RecyclerView so scrolling is blazing fast
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected ImageView image;
        protected TextView position;
        protected ImageView imageColor;
        protected ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.item_name);
            this.image = (ImageView) view.findViewById(R.id.item_image);
            this.position = (TextView) view.findViewById(R.id.item_position);
            this.imageColor = (ImageView) view.findViewById(R.id.item_image_background);
            this.progressBar = (ProgressBar) view.findViewById(R.id.item_progress_bar);
        }
    }
}