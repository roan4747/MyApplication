package com.example.owner.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView day;
    EditText edtDiary;
    Button btnSave, btnLoad, btnFontSize, btnDelete;
    DatePickerDialog datePickerDialog;
    String fileName;

    boolean bool=false; //OnDateSet이 2번 호출되는걸 방지하는데 쓰임
    Calendar cal = Calendar.getInstance();
    int cYear = cal.get(Calendar.YEAR);
    int cMonth = cal.get(Calendar.MONTH);
    int cDay = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("나만의 일기장");

        day = (TextView) findViewById(R.id.day);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLoad = (Button) findViewById(R.id.load);
        btnFontSize = (Button) findViewById(R.id.fontSize);
        btnDelete = (Button) findViewById(R.id.delete);

        edtDiary.setBackgroundColor(Color.GRAY);

        datePickerDialog = new DatePickerDialog(this,this,cYear,cMonth,cDay);

        day.setText(cYear + "년 " + (cMonth + 1) + "월 " + cDay + "일");  // TextView에 오늘 날짜 표시
        fileName = (cYear + "_" + (cMonth + 1) + "_" + cDay + ".txt");

        //오늘 일기 setting
        String strSDpath;
        String externalState=Environment.getExternalStorageState();
        if(externalState.equals(Environment.MEDIA_MOUNTED)){  //마운트 되어 있음
            strSDpath=Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        else{   //마운트 되지 않았을 때
            strSDpath=Environment.MEDIA_UNMOUNTED;
        }
        String result=null;
        FileInputStream inFs;
        try {                  //   오늘 일기를 보여주는 코드
            String dir=strSDpath+"/mydiary/"+fileName;
            File file=new File(dir);
            inFs=new FileInputStream(file);
            byte[] buffer=new byte[inFs.available()];
            inFs.read(buffer);
            inFs.close();
            result=new String(buffer);
            edtDiary.setText(result);  //내용을 EditText에
        }catch (IOException e){
           //일기 파일이 존재하지 않을 경우 (아무것도 않함)
        }

        day.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){//날짜 TextView 터치 시
                bool = false;
                datePickerDialog.show();
            }
        });
}

    public void OnClick(View v) {         //버튼 이벤트
        switch (v.getId()) {
            case R.id.btnSave:           //저장 버튼
                String strSDpath;
                String externalState=Environment.getExternalStorageState();
                if(externalState.equals(Environment.MEDIA_MOUNTED)){
                    strSDpath=Environment.getExternalStorageDirectory().getAbsolutePath();
                }
                else{
                    strSDpath=Environment.MEDIA_UNMOUNTED;
                }
                File file1 = new File(strSDpath + "/mydiary");
                if(!file1.isDirectory()){                          //mydiary 디레토리가 없으면
                    file1.mkdir();                                 //디렉토리 생성
                }
                File file2 = new File(strSDpath + "/mydiary/"+fileName);
                try {
                    FileOutputStream outFs = new FileOutputStream(file2);
                    String str = (edtDiary.getText().toString());
                        edtDiary.setText(str);
                    if(str.getBytes().length==0){
                        Toast.makeText(getApplicationContext(), "빈 일기는 저장되지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        outFs.write(str.getBytes());
                        outFs.close();
                        Toast.makeText(getApplicationContext(), fileName + "이 저장됨", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "저장 실패", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.fontSize:              // 글자 크기 버튼
                final String[] Array = new String[]{"크게","보통","작게"};
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(MainActivity.this);
                dlg2.setTitle("글씨 크기 조절");
                dlg2.setSingleChoiceItems(Array, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int size) {
                        edtDiary.setTextSize((size - 4) * -7);
                    }
                });
                dlg2.setNegativeButton("닫기", null);
                dlg2.show();
                break;

            case R.id.delete:                 //일기 삭제 버튼
                AlertDialog.Builder dlg=new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("삭제");
                dlg.setMessage(cYear + "년 " + (cMonth + 1) + "월 " + cDay + "일 \n일기를 삭제하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strSDpath;
                        String externalState = Environment.getExternalStorageState();
                        if (externalState.equals(Environment.MEDIA_MOUNTED)) {  //마운트 되어 있음
                            strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        } else {   //마운트 되지 않았을 때
                            strSDpath = Environment.MEDIA_UNMOUNTED;
                        }
                        File file = new File(strSDpath + "/mydiary/" + fileName);
                        if(file.exists()==false){
                            Toast.makeText(getApplicationContext(), "일기 파일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            file.delete();          // 일기 파일 삭제
                            edtDiary.setText(null); // EditText의 내용을 지움
                            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
                break;

            case R.id.load:  // 다시 읽기 버튼
                edtDiary.setText(load(fileName));
                break;
        }
    }

    public String load(String fName){    //파일이름을 받아 그 날의 일기내용을 리턴
        String strSDpath;
        String externalState=Environment.getExternalStorageState();
        if(externalState.equals(Environment.MEDIA_MOUNTED)){
            strSDpath=Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        else{
            strSDpath=Environment.MEDIA_UNMOUNTED;
        }
        String result=null;
        try {
            String dir=strSDpath+"/mydiary/"+fName;
            File file=new File(dir);
            FileInputStream iFs=new FileInputStream(file);
            byte[] buffer=new byte[iFs.available()];
            iFs.read(buffer);
            iFs.close();
            result=new String(buffer);
            Toast.makeText(getApplicationContext(), fName+"가 Load됨", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(getApplicationContext(), "일기가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return  result;   // 일기 내용 리턴
    }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if(bool==false){
            String Strday=(year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일");
            day.setText(Strday);
            fileName = (year + "_" + (monthOfYear+1) + "_" + dayOfMonth + ".txt");

            edtDiary.setText(load(fileName));
                bool=true;
                cYear=year;
                cMonth=monthOfYear;
                cDay=dayOfMonth;
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
