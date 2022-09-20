package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //Initialize Variable
    RecyclerView recyclerView;
    TextView tvEmpty;
    ArrayList<String> arrayList=new ArrayList<>(10);
    MainAdapter adapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("List of Items");
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_view);
        tvEmpty=findViewById(R.id.tv_empty);
        floatingActionButton=findViewById(R.id.floatingButton);


        //array values

        /*Intent i=getIntent();
        String name=i.getStringExtra("Name");
        String price=i.getStringExtra("Price");
        concat= String.valueOf(index);
        arrayList.add(concat);

         */
        arrayList.addAll(Arrays.asList("asd","twtw","asdassd"));




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