package com.example.administrator.event_scheduler;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

public class EventEdit extends AppCompatActivity implements OnClickListener {

    int mon, day;
    String month, day1, hour, minutes, scale;
    String title;
    boolean[][] date = new boolean[12][31];



    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        database = this.openOrCreateDatabase("Event_DB", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        final ImageButton plus_btn = (ImageButton) findViewById(R.id.plus_btn);
        plus_btn.setOnClickListener(this);
        plus_btn.setVisibility(View.GONE);
        CalendarView calendar = (CalendarView)findViewById(R.id.calendarView);



        //리스너 등록
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mon = month;
                day = dayOfMonth;
                Toast.makeText(EventEdit.this, ""+year+"/"+(month+1)+"/"
                        +dayOfMonth, Toast.LENGTH_SHORT).show();
                plus_btn.setVisibility(View.VISIBLE);
            }

        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent = getIntent();
        try {
            title = intent.getStringExtra("title");
            month = intent.getExtras().getString("month");
            day1 = intent.getExtras().getString("day");
            hour = intent.getExtras().getString("hour");
            minutes = intent.getExtras().getString("minutes");
            scale = intent.getExtras().getString("scale");
        }
        catch(NullPointerException e) {
            Log.w("Null pointer", "Null pointer error");
        }


        for(int i = 0; i <12; i++)
            date[i] = intent.getBooleanArrayExtra("date_array"+i);

        final DBHelper dbManager = new DBHelper(getApplicationContext(), "Event_DB", null, 1);
        dbManager.onCreate(database);
        dbManager.insert(title, Integer.parseInt(month), Integer.parseInt(day1), Integer.parseInt(hour), Integer.parseInt(minutes), Integer.parseInt(scale));
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddEvent.class);
        switch (v.getId()) {
            case R.id.plus_btn:
                intent.putExtra("month", Integer.toString(mon));
                intent.putExtra("day", Integer.toString(day));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_SHORT).show();
                break;
        }

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

        public void delete(String item) {
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행 삭제
            db.execSQL("DELETE FROM Event_DB WHERE item='" + item + "';");
            db.close();
        }

        public String getResult() {
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
