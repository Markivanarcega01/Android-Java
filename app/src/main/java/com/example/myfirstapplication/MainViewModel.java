package com.example.myfirstapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    MutableLiveData<String> mutableLiveData=new MutableLiveData<>();

    //create set text method

    public void setText(String s){
        mutableLiveData.setValue(s);
    }
    //get text method
    public MutableLiveData<String> getText(){
        return mutableLiveData;
    }
}
