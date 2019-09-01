package com.koko.www.androiddemo.component.ipc.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Hobby implements Parcelable {
    private String hobbyName;

    public Hobby(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hobbyName);
    }

    protected Hobby(Parcel in) {
        hobbyName = in.readString();
    }

    public static final Creator<Hobby> CREATOR = new Creator<Hobby>() {
        @Override
        public Hobby createFromParcel(Parcel in) {
            return new Hobby(in);
        }

        @Override
        public Hobby[] newArray(int size) {
            return new Hobby[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
