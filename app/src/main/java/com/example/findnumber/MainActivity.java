package com.example.findnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
Button bt_play;
TextView tv1;
TableLayout tableLayout;
int secrectNumber;
int score =10;
ArrayList<Button> listButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SecrectNumber();
        init();
        //showToast(secrectNumber+"");
    }
    private void init(){
        bt_play=(Button) findViewById(R.id.bt_play);
        tv1=(TextView) findViewById(R.id.tv1);
        tableLayout=(TableLayout) findViewById(R.id.tbl);

        tv1.setText("Bạn có: "+score+" lượt");
//Nút chơi lại
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });
//cấp phát bộ nhớ cho listButton
        listButtons= new ArrayList<Button>();

        //tạo 40 button chứa số từ 1->40
        createBnt();

    }
    public void createBnt(){
        View.OnClickListener clickBt =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //khai báo biến btTemp éo kiểu button của view
                Button btTemp= (Button) view;
                //khi bấm thì score -1
                score-=1;
                tv1.setText("Bạn có: "+score+" lượt");
                // trường hợp score =0
                if(score==0){
                    for(int i=0;i<listButtons.size();i++) {
                        //dísable tất cả nút
                        disableBnt(listButtons.get(i));
                    }
                    //đổi màu chữ và nền cho kết quả khi đã hết lượt chơi
                    listButtons.get(secrectNumber-1).setTextColor(Color.parseColor("#AC1D1D"));
                    listButtons.get(secrectNumber-1).setBackgroundColor(Color.parseColor("#53E6E1"));
                    tv1.setText("----GAME OVER----");

                }
                //khai báo biến btValue là giá trị của button được ấn
                int btValue=Integer.parseInt(btTemp.getText().toString());
                //nếu giá trị lớn hơn số cần tìm
                if(btValue>secrectNumber){
                    for(int i=btValue-1;i<listButtons.size();i++){
                        //disable click các button đánh số lớn hơn số cần tìm
                        disableBnt(listButtons.get(i));
                    }
                }
                else if(btValue<secrectNumber){
                    for(int i=btValue-1;i>=0;i--){
                        disableBnt(listButtons.get(i));
                    }
                }
                else{
                    for(int i=0;i<listButtons.size();i++) {
                        //disable click tất cả button
                        disableBnt(listButtons.get(i));
                    }
                    tv1.setText("SCORE: "+score);
                    //đổi màu chữ và nền cho kết quả khi thắng
                    btTemp.setTextColor(Color.parseColor("#AC1D1D"));
                    btTemp.setBackgroundColor(Color.parseColor("#53E6E1"));
                }

            }
        };
        //tạo ra 12 cột và 5 hàng Button, tổng 60 nút
        for(int i=1;i<=12;i++){
            TableRow tblRow = new TableRow(this);
            for(int j=1;j<=5;j++){
                Button bt_number=new Button(this);
                //số thứ tự = 5*(i-1)+j
                int btValue =5*(i-1)+j;
                bt_number.setText(btValue+"");
                bt_number.setTextColor(Color.parseColor("#000000"));
                bt_number.setBackgroundColor(Color.parseColor("#f0f0f0"));
                bt_number.setOnClickListener(clickBt);

//                if(btValue==secrectNumber){
//                    bt_number.setTextColor(Color.parseColor("#AC1D1D"));
//                    bt_number.setBackgroundColor(Color.parseColor("#53E6E1"));
//                }
                //lưu Button vào listButton
                listButtons.add(btValue-1,bt_number);
                //add button vào table row
                tblRow.addView(bt_number);
            }
            //add table row vào tableLayout
            tableLayout.addView(tblRow);
        }
    }
    public void disableBnt(Button bnt){
        bnt.setClickable(false);
        bnt.setTextColor(Color.parseColor("#CDD4D4"));
    }
    public void playAgain(){
        SecrectNumber();
//mỗi lần chơi lại thì reset lại score =10
        score=10;
        tv1.setText("Bạn có: "+score+" lượt");

        for(int i=0;i<listButtons.size();i++){
            //đặt lại màu nền ban đầu cho tất cả nút
            listButtons.get(i).setTextColor(Color.parseColor("#000000"));
            listButtons.get(i).setBackgroundColor(Color.parseColor("#f0f0f0"));
            //đặt lại clickAble cho nút
            listButtons.get(i).setClickable(true);
        }

//        listButtons.get(secrectNumber-1).setTextColor(Color.parseColor("#AC1D1D"));
//        listButtons.get(secrectNumber-1).setBackgroundColor(Color.parseColor("#53E6E1"));
      //  showToast(secrectNumber+"");
    }
    public void SecrectNumber(){

        Random rd=new Random();
        //lấy số ngẫu nhiên từ 1 đến 60: Công thức là: số đầu + nextint(khoảng cách 2 số)
        secrectNumber=1+ rd.nextInt(60);
    }
    public void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}