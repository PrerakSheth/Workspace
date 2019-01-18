package com.android.roomdatabasedemo;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.roomdatabasedemo.DatabaseHelper.SampleDatabaseHelper;
import com.android.roomdatabasedemo.Entity.College;
import com.android.roomdatabasedemo.Entity.University;

public class MainActivity extends AppCompatActivity {

    SampleDatabaseHelper sampleDatabaseHelper;
    Button btnAdd;
    EditText etUniversity;
    Button btnGetUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etUniversity = findViewById(R.id.etUniversity);
        btnGetUniversity = findViewById(R.id.btnGetUniversity);

        sampleDatabaseHelper = Room.databaseBuilder(getApplicationContext(), SampleDatabaseHelper.class, "sample-db").build();

        btnGetUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GetUniversityActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUniversity.getText().toString().trim().length() > 0) {
                    new DatabaseAsync().execute();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    etUniversity.setText("");
                    Toast.makeText(getApplicationContext(), "Your university is added.", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            University university = new University();
            university.setName(etUniversity.getText().toString());

            College college = new College();
            college.setId(1);
            college.setName("MyCollege");

            university.setCollege(college);
            //Now access all the methods defined in DaoAccess with sampleDatabase object
            sampleDatabaseHelper.dbOperationPerformDAO().insertOnlySingleRecord(university);

            //To update only name of university, change it and pass the object along with the primary key value.
//            university.setSlNo(1);
//            university.setName("ABCUniversity");
//            sampleDatabaseHelper.dbOperationPerformDAO().updateRecord(university);

            //To delete this record set the primary key and this will delete the record matching that primary key value.
//            University university = new University();
//            university.setSlNo(1);
//            sampleDatabaseHelper.dbOperationPerformDAO().deleteRecord(university);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
