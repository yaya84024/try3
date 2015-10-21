package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.database.Cursor;
import android.widget.EditText;
import android.webkit.WebViewClient;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    public static final String DB_NAME = "mydata.db"; // 資料庫名稱
    public static final String TABLE_NAME = "URL";   // 表格名稱
    SQLiteDatabase db;
    private Button btnInsert;
    private Button btnViewWeb;
    private EditText editText1;
    private WebView WebView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText) findViewById(R.id.editText1);
        WebView1 = (WebView) findViewById(R.id.webView1);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnViewWeb = (Button) findViewById(R.id.btnViewWeb);

        btnInsert.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //插入資料到資料表中
                try {
                    db.execSQL("insert into "+TABLE_NAME+" values('" + editText1.getText().toString() + "')");
                    editText1.setText("DB INSERT OK");
                } catch (Exception e) {
                    editText1.setText("DB INSERT FALL");
                }
            }
        });

        btnViewWeb.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //取得資料
                Cursor cursor = db.query(TABLE_NAME, new String[] { "WebURL" }, null, null, null, null, null);
                cursor.moveToLast();
                editText1.setText(cursor.getString(0));
                WebView1.setWebViewClient(new WebViewClient(){  });
                WebView1.loadUrl(cursor.getString(0));
            }
        });

        //打開或建立資料庫
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        //建立資料表
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (WebURL TEXT) ");
            editText1.setText("DB CREATE OK");
        } catch (Exception e) {
            editText1.setText("DB CREATE FALL");
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        deleteDatabase(DB_NAME);
        super.onDestroy();

    }
}


