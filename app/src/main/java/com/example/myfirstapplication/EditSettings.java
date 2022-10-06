package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditSettings extends AppCompatActivity {

    private EditText editName;
    private EditText editPrice;
    private Button editSave;
    private DatabaseHelper databaseHelper;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        editName=findViewById(R.id.editSettings_Name);
        editPrice=findViewById(R.id.editSettings_Price);
        editSave=findViewById(R.id.editSettings_Save);
        editName.addTextChangedListener(saveTextWatcher);
        editPrice.addTextChangedListener(saveTextWatcher);
        //sets the edit Text content
        Intent i=getIntent();
        String name=i.getStringExtra("Name");
        int price=i.getIntExtra("Price",0);
        index=i.getIntExtra("Index",0);
        editName.setText(name);
        editPrice.setText(String.valueOf(price));
    }
    //Disables the button is EditText is empty
    private TextWatcher saveTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name=editName.getText().toString().trim();
            String price=editPrice.getText().toString().trim();
            editSave.setEnabled(!name.isEmpty()&&!price.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    public void editSettings(View v) {
        String name=editName.getText().toString();
        int price=Integer.parseInt(editPrice.getText().toString());
        Log.e("index", String.valueOf(index));
        databaseHelper.updateData(new Data(name,price,index));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}