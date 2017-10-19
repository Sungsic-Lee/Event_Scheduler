package com.example.administrator.event_scheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.snatik.storage.Storage;

import java.io.File;
import java.util.ArrayList;

public class edit_templte extends AppCompatActivity {

    private Context mContext;
    ArrayList<CustomListInfo> mOrders;
    CustomListAdapter mAdapter;
    ListView mList;

    int list_id;
    String name[] = null, path[] = null, fileName[] = null, temp[][];

    Storage storage;
    private SQLiteDatabase database;

    EditText mTitleEdit;
    EditText mMemodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_templte);

        mTitleEdit = (EditText)findViewById(R.id.templte_title);
        mMemodata = (EditText)findViewById(R.id.templte_edit);
        database = this.openOrCreateDatabase("Event_DB", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        final DBHelper dbManager = new DBHelper(getApplicationContext(), "Templte_DB", null, 1);
        String read_DB = dbManager.serching_Templte_DB("Templte_DB");

        mList = (ListView) findViewById(R.id.mList);
        mOrders = new ArrayList<CustomListInfo>();

        mOrders.clear();
        
        if(!(read_DB == "")) {
            String read_DB_split[] = read_DB.split(";");



            for (int i = 0; i < read_DB_split.length; i++) {
                CustomListInfo item = new CustomListInfo(read_DB_split[i], true);
                mOrders.add(item);
                i+=1;
            }


            ListView listView = (ListView) findViewById(R.id.mList);
            mAdapter = new CustomListAdapter(this, R.layout.customlayout, mOrders);
            listView.setAdapter(mAdapter);
        }



        ListView listView = (ListView) findViewById(R.id.mList);
        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                //list view 클릭
                list_id = position;
            }

        }) ;

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //액션바 초기화
        invalidateOptionsMenu();

    }

    //back 버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.plus_btn).setVisible(false);
        menu.findItem(R.id.save_btn).setVisible(true);
        menu.findItem(R.id.load_btn).setVisible(false);
        menu.findItem(R.id.share_btn).setVisible(false);
        menu.findItem(R.id.delet_btn).setVisible(true);
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
                storage = new Storage(this.getApplicationContext());
                String path_final = storage.getInternalFilesDirectory() + File.separator + "template";


                String title_txt = mTitleEdit.getText().toString();
                String save_path = path_final + File.separator + title_txt+".txt";

                String memoData = mMemodata.getText().toString();
                if(!(title_txt == null) && !(memoData == null)) {
                    storage.createDirectory(path_final);
                    storage.createFile(save_path, memoData);
                    Log.d("Save Success", "Save Complet"+path_final+"   to   "+save_path);

                    final DBHelper dbManager = new DBHelper(getApplicationContext(), "Templte_DB", null, 1);
                    dbManager.onCreate(database);
                    dbManager.insert_Templte_DB("Templte_DB", title_txt, path_final+ File.separator, title_txt+".txt");
                    Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                }

                else
                    Log.d("File IO ERROR", "File Save Faliure");



                break;

            case R.id.delet_btn:


                break;
        }

        return super.onOptionsItemSelected(item);
    }

}


