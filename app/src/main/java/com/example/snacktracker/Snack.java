package com.example.snacktracker;
import java.util.UUID;
import java.util.Date;

public class Snack {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mFound;

    public Snack(){
        this(UUID.randomUUID());
    }

    public Snack(UUID id){
        mId = id;
        mDate = new Date();
    }

    public String getmTitle() {
        if (mTitle == null){
            return "None";
        }else{
            return mTitle;
        }
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismFound() {
        return mFound;
    }

    public void setmFound(boolean mFound) {
        this.mFound = mFound;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }
}
