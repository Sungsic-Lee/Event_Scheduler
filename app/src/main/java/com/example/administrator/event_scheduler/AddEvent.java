package com.example.administrator.event_scheduler;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import static com.example.administrator.event_scheduler.R.id.plus_btn;

//public class AddEvent extends FragmentActivity implements OnClickListener{
public class AddEvent extends AppCompatActivity implements OnClickListener {

    int scale = 0;
    boolean[][] date = new boolean[12][31];
    Button save_btn, exit_btn;
    EditText title_input, date_month_input, date_day_input, time_hour_input, time_minutes_input;
    SeekBar event_scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
//        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        save_btn = (Button) findViewById(R.id.Save_btn);
        exit_btn = (Button) findViewById(R.id.Exit_btn);
        save_btn.setOnClickListener(this);
        exit_btn.setOnClickListener(this);
        title_input = (EditText) findViewById(R.id.Title_input);
        date_month_input = (EditText) findViewById(R.id.Date_month_input);
        date_day_input = (EditText) findViewById(R.id.Date_day_input);
        time_hour_input = (EditText) findViewById(R.id.Time_hour_input);
        time_minutes_input = (EditText) findViewById(R.id.Time_minutes_input);
        event_scale = (SeekBar) findViewById(R.id.Event_scale_select_bar);

        Button plus_btn = (Button) findViewById(R.id.plus_btn);
        invalidateOptionsMenu();        //Actin Bar menu 를 다시 불러옴


        Intent intent = getIntent();
        date_month_input.setText(intent.getStringExtra("month"));
        date_day_input.setText(intent.getStringExtra("day"));


        event_scale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar event_scale, int progress, boolean fromUser) {
                scale = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) throws NumberFormatException {
        String title, month, day, hour, minutes;
        title = title_input.getText().toString();
        month = date_month_input.getText().toString();
        day = date_day_input.getText().toString();
        hour = time_hour_input.getText().toString();
        minutes = time_minutes_input.getText().toString();

        switch (v.getId()) {
            case R.id.Save_btn:
                if((title.getBytes().length != 0) && (month.getBytes().length !=0) && (day.getBytes().length !=0) && (hour.getBytes().length != 0) && (minutes.getBytes().length !=0)) {
                    Intent intent = new Intent(AddEvent.this, EventEdit.class);
                    intent.putExtra("title", title);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    intent.putExtra("hour", hour);
                    intent.putExtra("minutes", minutes);
                    intent.putExtra("scale", Integer.toString(scale));
                    setResult(RESULT_OK, intent);
                } else {
                    Intent intent = new Intent(AddEvent.this, EventEdit.class);
                    setResult(-1, intent);
                    this.finish();
                }
//                date[Integer.parseInt(date_month_input.getText().toString())][Integer.parseInt(date_day_input.getText().toString())] = true;
//                intent.putExtra("date_array", date);
                this.finish();
                break;

            case R.id.Exit_btn:
                Intent intent = new Intent(AddEvent.this, EventEdit.class);
                setResult(1, intent);
                this.finish();
                break;
        }
    }

    //back 버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.plus_btn).setVisible(false);
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
            case plus_btn:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
