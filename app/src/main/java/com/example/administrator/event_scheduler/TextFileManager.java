package com.example.administrator.event_scheduler;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017-10-19.
 */

public class TextFileManager {
    String savePath = Environment.getDataDirectory().getAbsolutePath();
    private static String FILE_NAME = "";
    //메모 내용을 저장할 파일 이름
    Context mContext = null;

    public TextFileManager(Context context) {
        mContext = context;
    }

    //파일에 메모를 저장하는 함수
    public void save(String strData, String name) {
        FILE_NAME = name;
        if(strData == null || strData.equals("")) {
            return;
        }

        FileOutputStream fosMemo = null;

        try {

                    savePath += "/memo_data";
            File file = new File(savePath);
            if(!file.exists()) {
                file.mkdir();
            }
            File savefile = new File(savePath+"/"+FILE_NAME);
            //파일에 데이터를 쓰기 위해서 output 스트림 생성
               fosMemo = mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            //파일에 메모 적기
                fosMemo.write(strData.getBytes());
                fosMemo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //저장된 메모를 불러오는 함수
    public  String load() {
        try {
            //파일에서 데이터를 읽기 위해서 input 스트림 생성
            FileInputStream fisMemo = mContext.openFileInput(FILE_NAME);

            //데이터를 읽어 온 뒤, String 타입 개게로 반환
            byte[] memoData = new byte[fisMemo.available()];
            while (fisMemo.read(memoData) != -1) {

            }

            return new String(memoData);
        } catch (IOException e) {

        }

        return "";

    }

    //저장된 메모를 삭제하는 함수
    public void delete() {
        mContext.deleteFile(FILE_NAME);
    }
}
