package com.cjw.evolution.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CJW on 2016/10/23.
 */

public class Follows implements Parcelable {

    private long id;
    private String created_at;

    private User followee;

    private User follower;

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

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
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
        dest.writeParcelable(this.follower, flags);
    }

    public Follows() {
    }

    protected Follows(Parcel in) {
        this.id = in.readLong();
        this.created_at = in.readString();
        this.followee = in.readParcelable(User.class.getClassLoader());
        this.follower = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Follows> CREATOR = new Parcelable.Creator<Follows>() {
        @Override
        public Follows createFromParcel(Parcel source) {
            return new Follows(source);
        }

        @Override
        public Follows[] newArray(int size) {
            return new Follows[size];
        }
    };
}
