package com.example.administrator.event_scheduler;

/**
 * Created by Administrator on 2017-10-19.
 */

public class CustomListInfo {
    private boolean mChecked;
    private String mListName;


    public CustomListInfo(String listname, boolean check) {

        this.mChecked = check;
        this.mListName = listname;
    }

    public boolean getCheck() {
        return mChecked;
    }

    public String getListName() {
        return mListName;
    }

}
