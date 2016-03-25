package com.example.roysatm.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_0;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_point;
    private Button button_plus;
    private Button button_minus;
    private Button button_divide;
    private Button button_multiply;
    private Button button_del;
    private Button button_c;
    private Button button_equal;

    private EditText editText;

    private Button button_rightParentheses;
    private Button button_leftParentheses;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      实例化按钮
        button_0 = (Button) findViewById(R.id.button_0);
        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);
        button_3 = (Button) findViewById(R.id.button_3);
        button_4 = (Button) findViewById(R.id.button_4);
        button_5 = (Button) findViewById(R.id.button_5);
        button_6 = (Button) findViewById(R.id.button_6);
        button_7 = (Button) findViewById(R.id.button_7);
        button_8 = (Button) findViewById(R.id.button_8);
        button_9 = (Button) findViewById(R.id.button_9);
        button_point = (Button) findViewById(R.id.button_point);
        button_plus = (Button) findViewById(R.id.button_plus);
        button_minus = (Button) findViewById(R.id.button_minus);
        button_divide = (Button) findViewById(R.id.button_divide);
        button_multiply = (Button) findViewById(R.id.button_multiply);
        button_del = (Button) findViewById(R.id.button_del);
        button_c = (Button) findViewById(R.id.button_c);
        button_equal = (Button) findViewById(R.id.button_equal);
        button_rightParentheses = (Button) findViewById(R.id.button_rightParentheses);
        button_leftParentheses = (Button) findViewById(R.id.button_leftParentheses);

//实例化显示屏
        editText = (EditText) findViewById(R.id.editText);

//设置点击事件
        button_0.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);
        button_c.setOnClickListener(this);
        button_del.setOnClickListener(this);
        button_plus.setOnClickListener(this);
        button_minus.setOnClickListener(this);
        button_multiply.setOnClickListener(this);
        button_divide.setOnClickListener(this);
        button_point.setOnClickListener(this);
        button_equal.setOnClickListener(this);
        button_leftParentheses.setOnClickListener(this);
        button_rightParentheses.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String str = editText.getText().toString().trim();

        switch (v.getId()) {
            case R.id.button_0:
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
            case R.id.button_point:
            case R.id.button_plus:
            case R.id.button_minus:
            case R.id.button_multiply:
            case R.id.button_divide:
            case R.id.button_leftParentheses:
            case R.id.button_rightParentheses:
//          自动清空显示上次计算的结果，方便下次运算输入
                if (flag) {
                    str = "";
                    flag = false;
                }
                editText.setText(str + ((Button) v).getText());
                break;
            case R.id.button_equal:
                editText.setText("" + CalcUnit.result(str));
                flag = true;
                break;
            case R.id.button_del:
                if (str != null && !str.equals("")) {
                    editText.setText(str.substring(0, str.length() - 1));
                }
                break;
            case R.id.button_c:
                editText.setText("");
                break;
        }
    }

}
