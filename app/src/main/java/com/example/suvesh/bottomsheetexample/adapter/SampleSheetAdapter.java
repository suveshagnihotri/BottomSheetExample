package com.example.suvesh.bottomsheetexample.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.suvesh.bottomsheetexample.R;
import com.example.suvesh.bottomsheetexample.model.ImagePath;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suvesh on 09/09/16.
 */
public class SampleSheetAdapter extends RecyclerView.Adapter<SampleSheetAdapter.ItemHolder> {
    private OnItemClickListener onItemClickListener;
    List<ImagePath> imagePathList = new ArrayList<>();
    private Context context;

    public SampleSheetAdapter( List<ImagePath> imagePathList, Context context ) {
        this.imagePathList = imagePathList;
        this.context=context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sheet_main, parent, false);
        return new ItemHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        try {

            Bitmap bmp = decodeURI(imagePathList.get(position).path);
            //BitmapFactory.decodeFile(mUrls[position].getPath());
            holder.imageView.setImageBitmap(bmp);
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return imagePathList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ItemHolder item, int position);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SampleSheetAdapter adapter;
        ImageView imageView;

        public ItemHolder(View itemView, SampleSheetAdapter parent) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.adapter = parent;
            imageView = (ImageView) itemView.findViewById(R.id.gallery_image);
        }


        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = adapter.getOnItemClickListener();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition());
            }
        }
    }

    public Bitmap decodeURI(String filePath){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Only scale if we need to
        // (16384 buffer for img processing)
        Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math.abs(options.outWidth - 100);
        if(options.outHeight * options.outWidth * 2 >= 16384){
            // Load, scaling to smallest power of 2 that'll get it <= desired dimensions
            double sampleSize = scaleByHeight
                    ? options.outHeight / 100
                    : options.outWidth / 100;
            options.inSampleSize =
                    (int)Math.pow(2d, Math.floor(
                            Math.log(sampleSize)/Math.log(2d)));
        }

        // Do the actual decoding
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[512];
        Bitmap output = BitmapFactory.decodeFile(filePath, options);

        return output;
    }
}
