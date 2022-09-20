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
    private EditText editTextName;
    private EditText editTextPrice;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_settings);
        editTextName=findViewById(R.id.addSettings_Name);
        editTextPrice=findViewById(R.id.addSettings_Price);
        saveButton=findViewById(R.id.addSettings_SaveButton);

        editTextName.addTextChangedListener(saveTextWatcher);
        editTextPrice.addTextChangedListener(saveTextWatcher);
    }
    private TextWatcher saveTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name=editTextName.getText().toString().trim();
            String price=editTextPrice.getText().toString().trim();
            saveButton.setEnabled(!name.isEmpty()&&!price.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void addSettings(View v) {
        Intent i = new Intent(this, MainActivity.class);
        String name=editTextName.getText().toString();
        String price=editTextPrice.getText().toString();
        i.putExtra("Name", name);
        i.putExtra("Price", price);
        startActivity(i);
    }

}