package com.example.administrator.event_scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class AddEvent extends AppCompatActivity implements OnClickListener{


    int scale = 0;
    boolean[][] date = new boolean[12][31];
    Button save_btn, exit_btn;
    EditText title_input, date_month_input, date_day_input, time_hour_input, time_minutes_input;
    SeekBar event_scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

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
        switch (v.getId()) {
            case R.id.Save_btn:
                Intent intent = new Intent(this, EventEdit.class);
                intent.putExtra("title", title_input.getText());
                intent.putExtra("month", date_month_input.getText().toString());
                intent.putExtra("day", date_day_input.getText().toString());
                intent.putExtra("hour", time_hour_input.getText().toString());
                intent.putExtra("minutes", time_minutes_input.getText().toString());
                intent.putExtra("scale", Integer.toString(scale));
                date[Integer.parseInt(date_month_input.getText().toString())][Integer.parseInt(date_day_input.getText().toString())] = true;
                intent.putExtra("date_array", date);
                startActivity(intent);
                this.finish();
                break;

            case R.id.Exit_btn:
                this.finish();
                break;
        }
    }


}
