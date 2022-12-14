package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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
            Data obj=new Data(cursor.getString(1),cursor.getInt(2),cursor.getInt(0));
            arrayList.add(obj);
        }
        if (arrayList.size()==0){
            tvEmpty.setVisibility(View.VISIBLE);
        }else{
            tvEmpty.setVisibility(View.GONE);
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
    int counter=0;
    @Override
    public void onBackPressed() {
        counter++;
        if (counter==2) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}