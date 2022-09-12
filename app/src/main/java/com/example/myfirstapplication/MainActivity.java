package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //Initialize Variable
    RecyclerView recyclerView;
    TextView tvEmpty;
    ArrayList<String> arrayList=new ArrayList<>();
    MainAdapter adapter;

    // MARK IVAN PEPE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("List of Items");
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_view);
        tvEmpty=findViewById(R.id.tv_empty);

        //array values
        arrayList.addAll(Arrays.asList("One","Two","Three","Four","Five",
                "Six","Seven","Eight","Nine","Ten","Eleven","Twelve","Thirteen"));

        //set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initialize adapter
        adapter=new MainAdapter(this, arrayList,tvEmpty);
        //set adapter
        recyclerView.setAdapter(adapter);
    }
}