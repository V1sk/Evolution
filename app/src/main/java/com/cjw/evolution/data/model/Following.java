package com.cjw.evolution.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CJW on 2016/10/23.
 */

public class Following implements Parcelable {

    private long id;
    private String created_at;

    private User followee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.created_at);
        dest.writeParcelable(this.followee, flags);
    }

    public Following() {
    }

    protected Following(Parcel in) {
        this.id = in.readLong();
        this.created_at = in.readString();
        this.followee = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Following> CREATOR = new Parcelable.Creator<Following>() {
        @Override
        public Following createFromParcel(Parcel source) {
            return new Following(source);
        }

        @Override
        public Following[] newArray(int size) {
            return new Following[size];
        }
    };
}
