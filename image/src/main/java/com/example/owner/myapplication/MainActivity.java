package com.example.owner.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    TextView text1, text2;
    Switch chkAgree;
    RadioGroup rGroup;
    RadioButton jelly, kitkat, lollipop;
    Button btnClose,btnReset;
    ImageView imgAndroid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("안드로이드 사진 보기");

        text1 = (TextView) findViewById(R.id.Text1);
        chkAgree = (Switch) findViewById(R.id.ChkAgree);

        text2 = (TextView) findViewById(R.id.Text2);
        rGroup = (RadioGroup) findViewById(R.id.RGroup);
        jelly = (RadioButton) findViewById(R.id.Jelly);
        kitkat = (RadioButton) findViewById(R.id.Kitkat);
        lollipop = (RadioButton) findViewById(R.id.Lollipop);

        btnClose = (Button) findViewById(R.id.BtnClose);
        btnReset = (Button) findViewById(R.id.BtnReset);
        imgAndroid = (ImageView) findViewById(R.id.ImgAndroid);

        chkAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (chkAgree.isChecked() == true) {
                    text2.setVisibility(android.view.View.VISIBLE);
                    rGroup.setVisibility(android.view.View.VISIBLE);

                } else {
                    text2.setVisibility(android.view.View.INVISIBLE);
                    rGroup.setVisibility(android.view.View.INVISIBLE);

                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                chkAgree.setChecked(false);
                imgAndroid.setVisibility(View.INVISIBLE);
            }
        });


        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int check) {
                switch (rGroup.getCheckedRadioButtonId()) {
                    case R.id.Jelly:
                        imgAndroid.setImageResource(R.drawable.jelly);
                        imgAndroid.setVisibility(View.VISIBLE);
                        break;
                    case R.id.Kitkat:
                        imgAndroid.setImageResource(R.drawable.kitkat);
                        imgAndroid.setVisibility(View.VISIBLE);
                        break;
                    case R.id.Lollipop:
                        imgAndroid.setImageResource(R.drawable.lollipop);
                        imgAndroid.setVisibility(View.VISIBLE);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "먼저 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }}