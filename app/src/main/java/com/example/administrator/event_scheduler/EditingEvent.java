package com.example.administrator.event_scheduler;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditingEvent extends AppCompatActivity {

    String view_id;
    EditText mMemoEdit = null;
    TextFileManager mTextFileManager = new TextFileManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_event);
        Intent intent = new Intent(EditingEvent.this, EventEdit.class);
        view_id = intent.getStringExtra("select_view");
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

        String memoData = mTextFileManager.load();
        mMemoEdit.setText(memoData);

        Toast.makeText(this, "불러오기 완료", Toast.LENGTH_SHORT).show();

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
        menu.findItem(R.id.plus_btn).setVisible(false);
        menu.findItem(R.id.save_btn).setVisible(true);
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
            case R.id.save_btn:
                String memoData = mMemoEdit.getText().toString();
                mTextFileManager.save(memoData, view_id);
                mMemoEdit.setText("");

                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
