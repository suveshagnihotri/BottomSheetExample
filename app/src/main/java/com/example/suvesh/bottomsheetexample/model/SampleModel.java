package com.example.suvesh.bottomsheetexample.model;

/**
 * Created by Suvesh on 09/09/16.
 */
public class SampleModel {
    private int titleId;
    private int imageId;

    public SampleModel(int titleId, int imageId) {
        this.titleId = titleId;
        this.imageId = imageId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
