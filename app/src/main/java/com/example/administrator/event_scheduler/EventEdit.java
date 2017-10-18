package com.example.administrator.event_scheduler;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

        //DB 관련
        final DBHelper dbManager = new DBHelper(getApplicationContext(), "Event_DB", null, 1);
        String read_DB = dbManager.serching(mon);
        String read_DB_split [];
        read_DB_split = read_DB.split(";");


        //ListView 관련
        ListView listView = (ListView)findViewById(R.id.event_list);
        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, read_DB_split);
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EventEdit.this, "click",Toast.LENGTH_SHORT).show();
                // List View 클릭
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        final DBHelper dbManager = new DBHelper(getApplicationContext(), "Event_DB", null, 1);
        dbManager.onCreate(database);
        dbManager.insert(title, month, day1, hour, minutes, scale);

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
            }
        } else if(resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "숫자를 입력해주십시오!", Toast.LENGTH_SHORT).show();
        } else if(resultCode == 1) {
            // EXIT 버튼 눌렀을때
        }
    }

    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }






    public class DBHelper extends SQLiteOpenHelper {

        // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // DB를 새로 생성할 때 호출되는 함수
        @Override
        public void onCreate(SQLiteDatabase db) {
            // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
            db.execSQL("CREATE TABLE IF NOT EXISTS Event_DB (_id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Month INTEGER, Day INTEGER, Hour INTEGER, Minutes INTEGER, Size INTEGER);");
        }

        // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void insert(String Title, int Month, int Day, int Hour, int Minutes, int Size) {
            // 읽고 쓰기가 가능하게 DB 열기
            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            // DB에 입력한 값으로 행 추가
            try {
                db.execSQL("INSERT INTO Event_DB VALUES(null, '" + Title + "','" + Month + "','" + Day + "','" + Hour + "','" + Minutes + "','" + Size + "');");
                db.setTransactionSuccessful();
            } catch (NumberFormatException e) {
                Log.w("Data Error", "data format error!");
                Toast.makeText(getApplicationContext(), "숫자를 입력해주세요", Toast.LENGTH_SHORT).show();
            } finally{
                db.endTransaction();
            }
            db.close();
        }

        public void update(String item, int price) {
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행의 가격 정보 수정
            db.execSQL("UPDATE Event_DB SET price=" + price + " WHERE item='" + item + "';");
            db.close();
        }

        public void delete() {
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행 삭제
            db.execSQL("DELETE FROM Event_DB");
            db.close();
        }

        public String serching(int month) {
            SQLiteDatabase db = getReadableDatabase();
            String title = "";
            int i = 0;
            Cursor cursor = db.rawQuery("SELECT * FROM Event_DB", null);
            while (cursor.moveToNext()) {
                if(cursor.getInt(2) == month) {
                    title += cursor.getString(2) + "월 " + cursor.getString(3) + "일\t"
                            + cursor.getString(4) + "시" + cursor.getString(5) + "분\t\t"
                            + cursor.getString(1) + ";";
                }
            }
            return title;
        }
        public String getResult(int month) {
            // 읽기가 가능하게 DB 열기
            SQLiteDatabase db = getReadableDatabase();
            String result = "";

            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            Cursor cursor = db.rawQuery("SELECT * FROM Event_DB", null);
            while (cursor.moveToNext()) {
                result += cursor.getString(0)
                        + " : "
                        + cursor.getString(1)
                        + " | "
                        + cursor.getInt(2)
                        + "원 "
                        + cursor.getString(3)
                        + "\n";
            }

            return result;
        }
    }


}
