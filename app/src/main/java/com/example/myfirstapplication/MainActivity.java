package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //Initialize Variable
    RecyclerView recyclerView;
    TextView tvEmpty;
    ArrayList<Data> arrayList=new ArrayList<>();
    MainAdapter adapter;
    FloatingActionButton floatingActionButton;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("List of Items");
        setContentView(R.layout.activity_main);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        recyclerView=findViewById(R.id.recycler_view);
        tvEmpty=findViewById(R.id.tv_empty);
        floatingActionButton=findViewById(R.id.floatingButton);

        //displays the data on shopMasterlist.db
        Cursor cursor=new DatabaseHelper(this).readData();
        while(cursor.moveToNext()){
            Data obj=new Data(cursor.getString(1),cursor.getInt(2));
            arrayList.add(obj);
        }


        //set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initialize adapter
        adapter=new MainAdapter(this, arrayList,tvEmpty,floatingActionButton);
        //set adapter
        recyclerView.setAdapter(adapter);
        //add button for input
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),AddSettings.class);
                startActivity(intent);
            }
        });
    }

}