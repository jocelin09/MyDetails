package com.example.mydetails.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.mydetails.DBHelper;
import com.example.mydetails.R;
import com.example.mydetails.databinding.ActivityHomePageBinding;
import com.example.mydetails.fragments.DetailsActivity;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity  {

    ActivityHomePageBinding activityHomePageBinding;
    
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    HomePageAdapter homePageAdapter;
    ArrayList<HomePageModel> homePageModels = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   // setContentView(R.layout.activity_home_page);
    activityHomePageBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
    
    dbHelper = new DBHelper(this);
    sqLiteDatabase = dbHelper.getWritableDatabase();
    
    setSupportActionBar(activityHomePageBinding.toolbar);
    getSupportActionBar().setTitle("Home");
    
    showEmptyList();
    
    
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    activityHomePageBinding.recyclerview.setLayoutManager(linearLayoutManager);
    activityHomePageBinding.recyclerview.setHasFixedSize(true);
    homePageAdapter = new HomePageAdapter(listData(), this);
    activityHomePageBinding.recyclerview.setItemAnimator(new DefaultItemAnimator());
    //activityHomePageBinding.recyclerview.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
    DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(activityHomePageBinding.recyclerview.getContext(),
            linearLayoutManager.getOrientation());
    activityHomePageBinding.recyclerview.addItemDecoration(mDividerItemDecoration);
    activityHomePageBinding.recyclerview.setAdapter(homePageAdapter);
    
    activityHomePageBinding.fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(HomePageActivity.this, DetailsActivity.class));
        }
    });
    
}

private void showEmptyList() {
    Cursor c = sqLiteDatabase.rawQuery("Select * from my_details_data", null);
    int count = c.getCount();
    c.close();
    
    if (count == 0) {
        activityHomePageBinding.nodata.setVisibility(View.VISIBLE);
    } else {
        activityHomePageBinding.nodata.setVisibility(View.GONE);
        
    }
}

public ArrayList<HomePageModel> listData(){
    String sql = "select * from my_details_data";
    ArrayList<HomePageModel> homePageModels = new ArrayList<>();
    Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
    if(cursor.moveToFirst()){
        do{
            String f_name = cursor.getString(1);
            String l_name = cursor.getString(2);
            String designation = cursor.getString(8);
            byte[] profile = cursor.getBlob(11);
            homePageModels.add(new HomePageModel(f_name, l_name, designation, profile));
            //homePageModels.add(new HomePageModel(f_name, l_name, designation));
        }while (cursor.moveToNext());
    }
    cursor.close();
    return homePageModels;
}

@Override
public void onBackPressed() {
    if (doubleBackToExitPressedOnce) {
        
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finishAffinity();
    }
    
    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
    
    new Handler().postDelayed(new Runnable() {
        
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    }, 2000);
}

}