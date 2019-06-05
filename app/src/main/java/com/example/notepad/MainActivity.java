package com.example.notepad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  AbsMethod {

    private CustomAdapter customAdapter;
    DatabaseHandler dbh = new DatabaseHandler(this);
    Button retrive;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        dbh = new DatabaseHandler(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomAdapter(MainActivity.this, getdata()) ;
        customAdapter.setAbsMethod(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        recyclerView.setAdapter(customAdapter);

    }

    public List<Todo> getdata() {
        List<Todo> todos = new ArrayList<>();
        Cursor cr = dbh.getalldata();
        if (cr.getCount() == 0) {
            Toast.makeText(MainActivity.this, "NO DATA PRESENT", Toast.LENGTH_SHORT).show();
        } else {
            cr.moveToFirst();
            do {
                Todo todo = new Todo(cr.getString(1), cr.getString(2).equals("1"));



                todos.add(todo);

            } while (cr.moveToNext());


        }

        return todos;
    }


    @Override
    public void onPressEnter(String val, boolean b) {

        customAdapter.update(val, b);
        //value of b to be setted
        dbh.adddata(val, b);

    }

    @Override
    public boolean onCheckChange(Todo todo) {
        boolean value = dbh.update(todo);
        return value;

    }
}


