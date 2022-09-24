package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddSettings extends AppCompatActivity {
    private EditText addTextName;
    private EditText addTextPrice;
    private Button saveButton;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_settings);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        addTextName=findViewById(R.id.addSettings_Name);
        addTextPrice=findViewById(R.id.addSettings_Price);
        saveButton=findViewById(R.id.addSettings_SaveButton);

        addTextName.addTextChangedListener(saveTextWatcher);
        addTextPrice.addTextChangedListener(saveTextWatcher);
    }
    private TextWatcher saveTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name=addTextName.getText().toString().trim();
            String price=addTextPrice.getText().toString().trim();
            saveButton.setEnabled(!name.isEmpty()&&!price.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void addSettings(View v) {
        String name=addTextName.getText().toString();
        int price=Integer.parseInt(addTextPrice.getText().toString());
        databaseHelper.insertData(new Data(name,price));
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}