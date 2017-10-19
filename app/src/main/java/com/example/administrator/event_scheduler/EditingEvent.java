package com.example.administrator.event_scheduler;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.snatik.storage.Storage;

import java.io.File;

public class EditingEvent extends AppCompatActivity {
    private static Context mContext;


    Storage storage;
    String path;

    EditText mMemoEdit;
    TextFileManager mTextFileManager = new TextFileManager(this);


    int selectListId, view_id;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_event);
        Intent intent = getIntent();
        view_id = intent.getIntExtra("ViewId", 0);
        id = Integer.toString(view_id);
        id +=".txt";
        Log.d("View id", Integer.toString(view_id));

        storage = new Storage(this.getApplicationContext());
        path = storage.getInternalFilesDirectory();
        String newDir = path + File.separator + id;


//        Toast.makeText(EditingEvent.this, view_id, Toast.LENGTH_SHORT).show();
        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle("ACTIONBAR");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invalidateOptionsMenu();

        //액션바 숨기기
        //hideActionBar();
        mMemoEdit = (EditText) findViewById(R.id.editevent);
            byte[] bytes = storage.readFile(newDir);
        if(!(bytes ==null)) {
            mMemoEdit.setText(new String(bytes));
        }

    }

    public void onClick(View v){
        switch (v.getId()) {
            //파일에 저장된 메모 텍스트 파일 불러오기
        }
    }
    //back 버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        //액션바 아이콘 숨김 설정
        menu.findItem(R.id.plus_btn).setVisible(false);
        menu.findItem(R.id.save_btn).setVisible(true);
        menu.findItem(R.id.load_btn).setVisible(true);
        menu.findItem(R.id.share_btn).setVisible(true);
        menu.findItem(R.id.delet_btn).setVisible(false);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "홈버튼", Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            case R.id.save_btn:
                String memoData = mMemoEdit.getText().toString();
                String newDir = path + File.separator + id;
                storage.createDirectory(path);
                storage.createFile(newDir, memoData);
                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
                this.finish();
                break;

            case R.id.load_btn:


                break;

            case R.id.share_btn:


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
