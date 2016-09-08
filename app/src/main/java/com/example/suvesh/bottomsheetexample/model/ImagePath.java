package com.example.suvesh.bottomsheetexample.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Suvesh on 09/09/16.
 */
public class ImagePath implements Parcelable {

    public String path;
    public boolean isSelected;

    public ImagePath() {
    }

    public ImagePath(String path, boolean isSelected) {
        this.path = path;
        this.isSelected = isSelected;
    }

    public ImagePath(String path) {
        this.path = path;
        this.isSelected = false;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected ImagePath(Parcel in) {
        this.path = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ImagePath> CREATOR = new Parcelable.Creator<ImagePath>() {
        @Override
        public ImagePath createFromParcel(Parcel source) {
            return new ImagePath(source);
        }

        @Override
        public ImagePath[] newArray(int size) {
            return new ImagePath[size];
        }
    };
}
