package com.sqlite.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sqlite.DAO.DBcrud;
import com.sqlite.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edittext;
    Button add, get;
    ListView listview;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getID();
    }

    public void getID() {
        edittext = (EditText) findViewById(R.id._edittextName);
        add = (Button) findViewById(R.id._buttonAdd);
        get = (Button) findViewById(R.id._buttonGet);
        listview = (ListView) findViewById(R.id._listview);
    }

    public void onAddclick(View view) {
        String input_name = edittext.getText().toString();
        //com.android.sqlitewithmvc.Model.StudentModel student = new com.android.sqlitewithmvc.Model.StudentModel();
        //student.setName(input_name);
        DBcrud dbcrud = new DBcrud(getApplicationContext());
        long in = dbcrud.insertstudent(input_name);
        Toast.makeText(getApplicationContext(), "" + in, Toast.LENGTH_LONG).show();
    }

    public void onGetclick(View view) {
        DBcrud dbcrud = new DBcrud(getApplicationContext());
        ArrayList<String> names = new ArrayList<String>();
        names = dbcrud.getallname();
        Log.v("names", "" + names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
        listview.setAdapter(adapter);
        //dbcrud.deleteByID(1);


    }
}
