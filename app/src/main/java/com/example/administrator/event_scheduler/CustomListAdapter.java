package com.example.administrator.event_scheduler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017-10-19.
 */

public class CustomListAdapter extends ArrayAdapter<CustomListInfo> {
    private List<CustomListInfo> mItems;
    private int mResourceId;
    private Context mContext;

    public CustomListAdapter(Context context, int textViewResourceId, List<CustomListInfo> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mItems = objects;
        this.mResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CustomListItemView mItemView;

        if (convertView == null) {
            mItemView = new CustomListItemView(mContext);
        }else{
            mItemView = (CustomListItemView)convertView;
        }

        CustomListInfo msg = mItems.get(position);
        mItemView.setMessage(msg);
        convertView = mItemView;

        return convertView;
    }

}
