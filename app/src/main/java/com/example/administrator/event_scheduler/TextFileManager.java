package com.example.administrator.event_scheduler;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Administrator on 2017-10-19.
 */

public class TextFileManager {
    private String state;
    private static String FILE_NAME = "";
    Context mContext;

    public TextFileManager(Context context) {
        mContext = context;
    }

    //파일에 메모를 저장하는 함수
    public void save(String strData, String name) {
        FILE_NAME = name;
        File file = new File(FILE_NAME+".txt") ;
        FileWriter fw = null ;
        String text = strData ;
        try {
            // open file.
            fw = new FileWriter(file) ;

            // write file.
            fw.write(text) ;

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        // close file.
        if (fw != null) {
            // catch Exception here or throw.
            try {
                fw.close() ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }

        //저장된 메모를 불러오는 함수
    public  String load() {
        File file = new File(FILE_NAME+".txt") ;
        FileReader fr = null ;
        char[] cbuf = new char[512] ;
        int size = 0 ;

        try {
            // open file.
            fr = new FileReader(file) ;

            // read file.
            while ((size = fr.read(cbuf)) != -1) {

            }

            return new String(cbuf);

        } catch (Exception e) {
            e.printStackTrace() ;
        }
        return "";

    }
    boolean checkExternalStorage() {
        state = Environment.getExternalStorageState();
        // 외부메모리 상태
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 읽기 쓰기 모두 가능
            Log.d("test", "외부메모리 읽기 쓰기 모두 가능");
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            //읽기전용
            Log.d("test", "외부메모리 읽기만 가능");
            return false;
        } else {
            // 읽기쓰기 모두 안됨
            Log.d("test", "외부메모리 읽기쓰기 모두 안됨 : "+ state);
            return false;
        }
    }

    //저장된 메모를 삭제하는 함수
    public void delete() {
        mContext.deleteFile(FILE_NAME);
    }
}
