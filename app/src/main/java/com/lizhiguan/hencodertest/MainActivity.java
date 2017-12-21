package com.lizhiguan.hencodertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView mList;
    ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        init();
    }

    private void init() {
        mList = (ListView) findViewById(R.id.main_list);
        mList.setOnItemClickListener(this);
        mData = new ArrayList<>(8);
        mData.add("PracticeDraw1");
        mData.add("PracticeDraw2");
        mData.add("PracticeDraw3");
        mData.add("PracticeDraw4");
        mData.add("PracticeDraw5");
        mData.add("PracticeDraw6");
        mData.add("PracticeDraw7");
        mData.add("PracticeDrawMock");
        mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, com.hencoder.hencoderpracticedraw1.MainActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, com.hencoder.hencoderpracticedraw2.MainActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, com.hencoder.hencoderpracticedraw3.MainActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, com.hencoder.hencoderpracticedraw4.MainActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, com.hencoder.hencoderpracticedraw5.MainActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, com.hencoder.hencoderpracticedraw6.MainActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, com.hencoder.hencoderpracticedraw7.MainActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, com.hencoder.hencoderpracticedrawmock.MainActivity.class));
                break;
        }
    }
}
