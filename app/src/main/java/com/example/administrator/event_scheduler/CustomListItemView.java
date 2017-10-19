package com.example.administrator.event_scheduler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-10-19.
 */

public class CustomListItemView extends LinearLayout {
    private Context mContext;
    private View childView = null;
    private edit_templte gAct;
    private CustomListInfo mItem;

    private CheckBox mCheckBox;
    private TextView mListName;

    boolean mCheckState;
    String mstrListName;

    public CustomListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        gAct = (edit_templte) mContext;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        childView = inflater.inflate(R.layout.customlayout, null);
        LayoutParams ll = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addView(childView, ll);

        //mCheckBox = (CheckBox)findViewById(R.id.templte_check);
        mListName = (TextView)findViewById(R.id.templte_title);

    }

    public CustomListItemView(Context context) {
        super(context);
        mContext = context;
        gAct = (edit_templte) mContext;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        childView = inflater.inflate(R.layout.customlayout, null);
        LayoutParams ll = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addView(childView, ll);

        //mCheckBox = (CheckBox)findViewById(R.id.templte_check);
        mListName = (TextView)findViewById(R.id.templte_title);
    }

    public void setMessage(CustomListInfo msg) {
        mItem = msg;

        //mCheckState = mItem.getCheck();
        mstrListName = mItem.getListName();

        /*if(mCheckState == true){
            mCheckBox.setChecked(true);
        }else{
            mCheckBox.setChecked(false);
        }
        */

        mListName.setText(mstrListName);
    }
}
