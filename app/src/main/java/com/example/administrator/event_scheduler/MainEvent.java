package com.example.administrator.event_scheduler;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//public class MainEvent extends FragmentActivity implements View.OnClickListener{
public class MainEvent extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        //액션바 숨기기
        hideActionBar();

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
        switch (v.getId()) {
            case R.id.event_btn:
                Intent intent1 = new Intent(this, EventEdit.class);
                startActivity(intent1);
                break;

            case R.id.temp_btn:
                Intent intent2 = new Intent(this, edit_templte.class);
                startActivity(intent2);
                break;

            case R.id.group_btn:

                break;
        }

    }


    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null)
            actionBar.hide();
    }
    //back 버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "홈버튼", Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            case R.id.plus_btn:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
