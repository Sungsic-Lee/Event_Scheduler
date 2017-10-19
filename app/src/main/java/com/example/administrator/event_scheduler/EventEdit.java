package com.example.administrator.event_scheduler;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//public class EventEdit extends FragmentActivity implements OnClickListener {
public class EventEdit extends AppCompatActivity implements OnClickListener {

    int mon, day;
    int month = 0, day1 = 0, hour = 0, minutes = 0, scale = 0;
    String title, temp[], read_DB, read_DB_split[];
    boolean[][] date = new boolean[12][31];
    Calendar calendar = Calendar.getInstance();
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        ListView listView = (ListView)findViewById(R.id.event_list);
        //dbManager.delete();           //DB 초기화

        SimpleDateFormat Date = new SimpleDateFormat("MM"+","+"dd");
        String time = Date.format(calendar.getTime());
        temp = time.split(",");
        mon = Integer.parseInt(temp[0]);
        day = Integer.parseInt(temp[1]);

        final DBHelper dbManager = new DBHelper(getApplicationContext(), "Event_DB", null, 1);
        String read_DB = dbManager.serching_Event_DB("Event_DB", mon);
        read_DB_split = read_DB.split(";");
        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, read_DB_split);
        listView.setAdapter(adapter1);

        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle("ACTIONBAR");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //액션바 숨기기
        //hideActionBar();
        database = this.openOrCreateDatabase("Event_DB", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        final ImageButton plus_btn = (ImageButton) findViewById(R.id.plus_btn);
        CalendarView calendar = (CalendarView)findViewById(R.id.calendarView);


        //리스너 등록
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mon = month+1;
                day = dayOfMonth;
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        ListView listView = (ListView)findViewById(R.id.event_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // List View 클릭
                Intent intent = new Intent(EventEdit.this, EditingEvent.class);
                intent.putExtra("ViewId", position);

                startActivityForResult(intent, 2);


            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }

    }

    //back 버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.plus_btn).setVisible(true);
        menu.findItem(R.id.save_btn).setVisible(false);
        menu.findItem(R.id.load_btn).setVisible(false);
        menu.findItem(R.id.share_btn).setVisible(false);
        menu.findItem(R.id.delet_btn).setVisible(false);
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
                Intent intent = new Intent(EventEdit.this, AddEvent.class);
                intent.putExtra("month", Integer.toString(mon));
                intent.putExtra("day", Integer.toString(day));
                startActivityForResult(intent, 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if(resultCode ==RESULT_OK) {
            if (requestCode == 1) {
                title = data.getStringExtra("title");
                month = Integer.parseInt(data.getStringExtra("month"));
                day1 = Integer.parseInt(data.getStringExtra("day"));
                hour = Integer.parseInt(data.getStringExtra("hour"));
                minutes = Integer.parseInt(data.getStringExtra("minutes"));
                scale = Integer.parseInt(data.getStringExtra("scale"));

                final DBHelper dbManager = new DBHelper(getApplicationContext(), "Event_DB", null, 1);
                dbManager.onCreate(database);
                dbManager.insert_Event_DB("Event_DB", title, month, day1, hour, minutes, scale);


                String read_DB = dbManager.serching_Event_DB("Event_DB", mon);
                read_DB_split = read_DB.split(";");
                //read_DB_split = read_DB.split(";");
                ListView listView = (ListView)findViewById(R.id.event_list);
                ArrayAdapter<String> adapter1;
                adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, read_DB_split);
                listView.setAdapter(adapter1);
            }
        } else if(resultCode == -1) {
            Toast.makeText(this, "숫자를 입력해주십시오!", Toast.LENGTH_SHORT).show();
        } else if(resultCode == 1) {
            // EXIT 버튼 눌렀을때
        }
        else {

        }
    }

    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }



}
