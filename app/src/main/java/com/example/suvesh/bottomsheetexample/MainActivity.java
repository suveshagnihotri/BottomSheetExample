package com.example.suvesh.bottomsheetexample;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.suvesh.bottomsheetexample.adapter.SampleSheetAdapter;
import com.example.suvesh.bottomsheetexample.model.ImagePath;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private BottomSheetDialog dialog;
    private Context context;
    public List<ImagePath> imagePathList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        ButterKnife.bind(this);
        imageMediaQuery();
    }

    @OnClick(R.id.showButton)
    void onShowButtonClick() {
        //behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        createDialog();
    }

    private boolean dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            return true;
        }

        return false;
    }

    private void createDialog() {
        if (dismissDialog()) {
            return;
        }

        SampleSheetAdapter adapter = new SampleSheetAdapter(imagePathList,MainActivity.this);
        adapter.setOnItemClickListener(new SampleSheetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SampleSheetAdapter.ItemHolder item, int position) {
                //dismissDialog();
            }
        });
        View view = getLayoutInflater().inflate(R.layout.sheet_main, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //for horizontal Recycler view
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        //For grid view
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
    }

    private void imageMediaQuery() {
        ImagePath imagePath;
        String[] projection = new String[]{
                MediaStore.MediaColumns.DATA,
                //MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String BUCKET_GROUP_BY =
                "1) GROUP BY 1,(1";
        String BUCKET_ORDER_BY = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        Cursor cur = context.getContentResolver().query(images,
                projection, // Which columns to return
                BUCKET_GROUP_BY,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                BUCKET_ORDER_BY        // Ordering
        );
        if (cur != null) {
            Log.i("ListingImages", " query count=" + cur.getCount());
        }
        if (cur != null && cur.moveToFirst()) {
            String bucket;
            String path;
            int bucketColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int pathColumn = cur.getColumnIndex(MediaStore.MediaColumns.DATA);
            do {
                bucket = cur.getString(bucketColumn);
                path = cur.getString(pathColumn);
                imagePath = new ImagePath(path);
                imagePathList.add(imagePath);
            } while (cur.moveToNext());
            cur.close();
        }
    }
}
