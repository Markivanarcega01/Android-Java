package com.example.myfirstapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements Filterable {
    Activity activity;
    ArrayList<Data> arrayList;
    TextView tvEmpty;
    MainViewModel mainViewModel;
    boolean isEnable=false;
    boolean isSelectAll= false;
    ArrayList<Data> selectList=new ArrayList<Data>();
    FloatingActionButton floatingActionButton;
    private DatabaseHelper databaseHelper;
    ArrayList<Integer> multipleDelete=new ArrayList<>();
    List<Data> listed;


    //create constructor
    public MainAdapter(Activity activity, ArrayList<Data> arrayList, TextView tvEmpty, FloatingActionButton floatingActionButton){
        this.activity=activity;
        this.arrayList=arrayList;
        this.tvEmpty=tvEmpty;
        this.floatingActionButton=floatingActionButton;
        this.listed=new ArrayList<>(arrayList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
        //View model
        mainViewModel= ViewModelProviders.of((FragmentActivity) activity).get(MainViewModel.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(arrayList.get(position).getName()+"\n"+"???"+arrayList.get(position).getPrice());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //check condition
                if (!isEnable){
                    //when action mode is not enable
                    //initialize action mode
                    ActionMode.Callback callback=new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                            // initialize menu inflater
                            MenuInflater menuInflater=actionMode.getMenuInflater();
                            //Inflate menu
                            menuInflater.inflate(R.menu.menu,menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                            //when action mode is prepare
                            //set isEnable true
                            isEnable=true;
                            //create method
                            ClickItem(holder);
                            mainViewModel.getText().observe((LifecycleOwner) activity, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    //check condition
                                    //hide edit button if selected items is not equal to 1
                                    if (selectList.size()==1){
                                        menu.findItem(R.id.menu_edit).setVisible(true);
                                    } else{
                                        menu.findItem(R.id.menu_edit).setVisible(false);
                                    }
                                    if (selectList.size()>0){
                                        floatingActionButton.hide();
                                    } else{
                                        floatingActionButton.show();
                                    }
                                    //when text change
                                    //set text on action mode title
                                    actionMode.setTitle(String.format("%s Selected",s));
                                }
                            });
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                            //when click on action mode item
                            // get item id
                            int id=menuItem.getItemId();
                            switch (id){
                                case R.id.menu_delete:
                                    databaseHelper=new DatabaseHelper(activity.getApplicationContext());
                                    for (Data s:selectList){
                                        int x=arrayList.get(position).getIndex();
                                        listed.remove(s);
                                        multipleDelete.add(x);
                                        arrayList.remove(s);
                                    }
                                    String convert=multipleDelete.toString().replace("[","").replace("]","");
                                    databaseHelper.deleteData(new Data(convert));
                                    if (arrayList.size()==0) {
                                        //when array list is empty
                                        //visible text view
                                        tvEmpty.setVisibility(view.VISIBLE);
                                    }
                                    //finish action mode
                                    actionMode.finish();
                                    break;
                                case R.id.menu_select_all:
                                    //when click on select all
                                    //check condition
                                    if (selectList.size()==arrayList.size()){
                                        //when all item selected
                                        // set isSelected false
                                        isSelectAll=false;
                                        selectList.clear();
                                    }else{
                                        //when all item unselected
                                        //set isSelect all true
                                        isSelectAll=true;
                                        selectList.clear();
                                        //add all value in select array list
                                        selectList.addAll(arrayList);
                                    }
                                    //set text on view model
                                    mainViewModel.setText(String.valueOf(selectList.size()));
                                    //notify adapter
                                    notifyDataSetChanged();
                                    break;
                                 case R.id.menu_edit:
                                    //when click on edit
                                    //proceeds to a new screen
                                     Intent intent = new Intent(view.getContext(), EditSettings.class);
                                     //select the input from the database
                                     String name=arrayList.get(position).getName();
                                     int price=arrayList.get(position).getPrice();
                                     int index=arrayList.get(position).getIndex();
                                     intent.putExtra("Name",name);
                                     intent.putExtra("Price",price);
                                     intent.putExtra("Index",index);
                                     //opens the next activity
                                     view.getContext().startActivity(intent);

                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode actionMode) {
                            //when action mode is destroy
                            //set isEnable false
                            isEnable=false;
                            isSelectAll=false;
                            selectList.clear();
                            floatingActionButton.show();
                            notifyDataSetChanged();
                        }
                    };
                    //start action mode
                    ((AppCompatActivity) view.getContext()).startActionMode(callback);
                }else{
                    //when action mode is already enable
                    //call method
                    ClickItem(holder);
                }
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEnable){
                    //when action is enable
                    //call method
                    ClickItem(holder);
                }else{
                    //when action is not enable
                    Toast.makeText(activity,"You clicked"+ arrayList.get(holder.getAdapterPosition())
                    ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        //check condition
        if (isSelectAll){
            //when all value selected
            //visible all check box image
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            //set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else{
            //when all value unselected
            //hide all check box image
            holder.ivCheckBox.setVisibility(View.GONE);
            //set bg color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }


    private void ClickItem(ViewHolder holder) {
        //get selected item value
        Data s=arrayList.get(holder.getAdapterPosition());
        //check condition
        if (holder.ivCheckBox.getVisibility()==View.GONE){
            //when item not selected
            //visible check box image
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            //set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            //add value in select array list
            selectList.add(s);
        }else{
            //when item selected
            //hide check box image
            holder.ivCheckBox.setVisibility(View.GONE);
            //set background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            //remove value from select array list
            selectList.remove(s);
        }
        mainViewModel.setText(String.valueOf(selectList.size()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List <Data> filteredList= new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filteredList.addAll(listed);
            }else{
                for(Data item:listed){
                    if (item.getName().toLowerCase().contains(charSequence.toString())){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }
        //runs on ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrayList.clear();
            arrayList.addAll((Collection<? extends Data>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView ivCheckBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_view);
            ivCheckBox=itemView.findViewById(R.id.iv_check_box);
        }
    }
}
