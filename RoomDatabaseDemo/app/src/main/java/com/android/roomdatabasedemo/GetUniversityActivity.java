package com.android.roomdatabasedemo;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.roomdatabasedemo.DatabaseHelper.SampleDatabaseHelper;
import com.android.roomdatabasedemo.Entity.University;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

public class GetUniversityActivity extends AppCompatActivity {

    TextView tvUniversity;
    SampleDatabaseHelper sampleDatabaseHelper;
    List<University> list = new ArrayList<>();
    TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_university);

        tvUniversity = findViewById(R.id.tvUniversity);
        tvNoData = findViewById(R.id.tvNoData);
        sampleDatabaseHelper = Room.databaseBuilder(getApplicationContext(), SampleDatabaseHelper.class, "sample-db").allowMainThreadQueries().build();


        list = sampleDatabaseHelper.dbOperationPerformDAO().fetchAllData();

        if (list.size() > 0) {
            tvNoData.setVisibility(View.GONE);
            String str = "";
            for (int i = 0; i < list.size(); i++) {
                Log.e("University", list.get(i).getName());
                str = str + "UniversityName " + (i + 1) + ":" + "  " + (list.get(i).getName()) + "\n";
                tvUniversity.setText(str);
            }
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
    }
}
