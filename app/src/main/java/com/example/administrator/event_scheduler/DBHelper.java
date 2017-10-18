package com.example.administrator.event_scheduler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017-10-19.
 */

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

    public void insert(String DB_Name, String Title, int Month, int Day, int Hour, int Minutes, int Size) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        // DB에 입력한 값으로 행 추가
        try {
            db.execSQL("INSERT INTO "+DB_Name+" VALUES(null, '" + Title + "','" + Month + "','" + Day + "','" + Hour + "','" + Minutes + "','" + Size + "');");
            db.setTransactionSuccessful();
        } catch (NumberFormatException e) {
            Log.w("Data Error", "data format error!");
            //Toast.makeText(getApplicationContext(), "숫자를 입력해주세요", Toast.LENGTH_SHORT).show();
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

    public void delete(String DB_Name) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM " + DB_Name);
        db.close();
    }

    public String serching(String DB_Name, int month) {
        SQLiteDatabase db = getReadableDatabase();
        String title = "";
        int i = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " +DB_Name, null);
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