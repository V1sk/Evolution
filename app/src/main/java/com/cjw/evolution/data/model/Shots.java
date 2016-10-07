package com.cjw.evolution.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by CJW on 2016/7/19.
 */
public class Shots implements Parcelable {

    private static final int[] NORMAL_IMAGE_SIZE = new int[] { 400, 300 };
    private static final int[] TWO_X_IMAGE_SIZE = new int[] { 800, 600 };

    private long id;
    private String title;
    private String description;
    private int width;
    private int height;

    private ImagesBean images;
    private int views_count;
    private int likes_count;
    private int comments_count;
    private int attachments_count;
    private int rebounds_count;
    private int buckets_count;
    private String created_at;
    private String updated_at;
    private String html_url;
    private String attachments_url;
    private String buckets_url;
    private String comments_url;
    private String likes_url;
    private String projects_url;
    private String rebounds_url;
    private boolean animated;

    private User user;

    private Team team;
    private List<String> tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttachments_count() {
        return attachments_count;
    }

    public void setAttachments_count(int attachments_count) {
        this.attachments_count = attachments_count;
    }

    public int getRebounds_count() {
        return rebounds_count;
    }

    public void setRebounds_count(int rebounds_count) {
        this.rebounds_count = rebounds_count;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public void setBuckets_count(int buckets_count) {
        this.buckets_count = buckets_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getAttachments_url() {
        return attachments_url;
    }

    public void setAttachments_url(String attachments_url) {
        this.attachments_url = attachments_url;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public void setBuckets_url(String buckets_url) {
        this.buckets_url = buckets_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public String getProjects_url() {
        return projects_url;
    }

    public void setProjects_url(String projects_url) {
        this.projects_url = projects_url;
    }

    public String getRebounds_url() {
        return rebounds_url;
    }

    public void setRebounds_url(String rebounds_url) {
        this.rebounds_url = rebounds_url;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getBestImage() {
        return !TextUtils.isEmpty(getImages().getHidpi()) ? getImages().getHidpi() : getImages().getNormal();
    }

    public int[] bestSize() {
        return !TextUtils.isEmpty(getImages().getHidpi()) ? TWO_X_IMAGE_SIZE : NORMAL_IMAGE_SIZE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeParcelable(this.images, flags);
        dest.writeInt(this.views_count);
        dest.writeInt(this.likes_count);
        dest.writeInt(this.comments_count);
        dest.writeInt(this.attachments_count);
        dest.writeInt(this.rebounds_count);
        dest.writeInt(this.buckets_count);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.html_url);
        dest.writeString(this.attachments_url);
        dest.writeString(this.buckets_url);
        dest.writeString(this.comments_url);
        dest.writeString(this.likes_url);
        dest.writeString(this.projects_url);
        dest.writeString(this.rebounds_url);
        dest.writeByte(this.animated ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.team, flags);
        dest.writeStringList(this.tags);
    }

    public Shots() {
    }

    protected Shots(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.description = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.images = in.readParcelable(ImagesBean.class.getClassLoader());
        this.views_count = in.readInt();
        this.likes_count = in.readInt();
        this.comments_count = in.readInt();
        this.attachments_count = in.readInt();
        this.rebounds_count = in.readInt();
        this.buckets_count = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.html_url = in.readString();
        this.attachments_url = in.readString();
        this.buckets_url = in.readString();
        this.comments_url = in.readString();
        this.likes_url = in.readString();
        this.projects_url = in.readString();
        this.rebounds_url = in.readString();
        this.animated = in.readByte() != 0;
        this.user = in.readParcelable(User.class.getClassLoader());
        this.team = in.readParcelable(Team.class.getClassLoader());
        this.tags = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Shots> CREATOR = new Parcelable.Creator<Shots>() {
        @Override
        public Shots createFromParcel(Parcel source) {
            return new Shots(source);
        }

        @Override
        public Shots[] newArray(int size) {
            return new Shots[size];
        }
    };
}
