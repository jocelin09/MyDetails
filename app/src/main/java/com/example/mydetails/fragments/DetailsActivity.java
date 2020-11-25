package com.example.mydetails.fragments;

import android.content.Intent;
import android.os.Bundle;

import com.example.mydetails.R;
import com.example.mydetails.databinding.ActivityDetailsBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding activityDetailsBinding;
private NavArgument nameArg ;
String f_name = "test";

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   // setContentView(R.layout.activity_details);
    activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
    
    try {
    
        setSupportActionBar(activityDetailsBinding.toolbar);
    
        
        Intent intent = getIntent();
        f_name = intent.getStringExtra("f_name");
        
       // if (f_name.equals("null") || f_name.equals(null) || f_name.isEmpty() ||f_name.length() == 0 || f_name.equals("") )
        if (f_name != null)
        {
            System.out.println(" if condition ");
            nameArg = new NavArgument.Builder().setDefaultValue(f_name).build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavInflater navInflater = navController.getNavInflater();
            NavGraph navGraph = navInflater.inflate(R.navigation.nav_graph);
            navGraph.addArgument("f_name", nameArg);
            navController.setGraph(navGraph);
        }
        else
        {
            System.out.println(" else condition ");
    
          //  nameArg = new NavArgument.Builder().setDefaultValue(f_name).build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavInflater navInflater = navController.getNavInflater();
            NavGraph navGraph = navInflater.inflate(R.navigation.nav_graph);
            //navGraph.addArgument("f_name", nameArg);
            navController.setGraph(navGraph);
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("e = " + e);
        
    }
    
    
}

}