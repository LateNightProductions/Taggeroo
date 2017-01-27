package com.latenightproductions.taggeroo.data.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject {

    @PrimaryKey
    private long id;

    private String text;

    private String memo;

    private Date timestamp;

    //================================================================================
    // Getters
    //================================================================================

    public Date getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public String getMemo() {
        return memo;
    }

    public long getId() {
        return id;
    }

    //================================================================================
    // Setters
    //================================================================================

    public void setId(long id) {
        this.id = id;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
