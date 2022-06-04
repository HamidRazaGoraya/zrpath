package com.esafirm.imagepicker.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.R;
import com.esafirm.imagepicker.model.Image;

import java.util.List;

/**
 * Created by rkoshti on 5/17/2017.
 */

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.MyViewHolder> {

    private List<Image> selectedImage;
    private Context context;

    public SelectedImageAdapter(List<Image> selectedImage, Context context) {
        this.selectedImage = selectedImage;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ef_imagepicker_item_selected, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (selectedImage != null) {
            Image image = selectedImage.get(position);

            Glide.with(context)
                    .load(image.getPath())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return selectedImage.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView,imageViewClose;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_selected);
        }
    }
}
