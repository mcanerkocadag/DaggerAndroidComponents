package com.example.daggerandroidcomponents.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Data {
    private String last_name;

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("first_name")
    @Expose
    private String first_name;

    public Data(String last_name, String id, String avatar, String first_name) {
        this.last_name = last_name;
        this.id = id;
        this.avatar = avatar;
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Override
    public String toString() {
        return "ClassPojo [last_name = " + last_name + ", id = " + id + ", avatar = " + avatar + ", first_name = " + first_name + "]";
    }
}
