package com.example.administrator.event_scheduler;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainEvent extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        SQLiteDatabase database = openOrCreateDatabase("schedule.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Button Event_btn = (Button) findViewById(R.id.event_btn);
        Button Temp_btn = (Button) findViewById(R.id.temp_btn);
        Button Group_btn = (Button) findViewById(R.id.group_btn);
        Event_btn.setOnClickListener(this);
        Temp_btn.setOnClickListener(this);
        Group_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EventEdit.class);
        switch (v.getId()) {
            case R.id.event_btn:
                startActivity(intent);
                break;

            case R.id.temp_btn:

                break;

            case R.id.group_btn:
                break;
        }

    }

}
