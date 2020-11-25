package com.example.mydetails.homepage;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mydetails.DBHelper;
import com.example.mydetails.R;
import com.example.mydetails.fragments.DetailsActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder> {

ArrayList<HomePageModel> homePageModelArrayList;
Context context;
HomePageModel homePageModel;
DBHelper dbHelper;
SQLiteDatabase sqLiteDatabase;



public HomePageAdapter(ArrayList<HomePageModel> homePageModelArrayList, Context context) {
    this.homePageModelArrayList = homePageModelArrayList;
    this.context = context;
}

@NonNull
@Override
public HomePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.homepage_lists, parent, false);
    
    return new HomePageViewHolder(itemView);}

@Override
public void onBindViewHolder(@NonNull final HomePageViewHolder holder, final int position) {
    dbHelper = new DBHelper(context);
    sqLiteDatabase = dbHelper.getWritableDatabase();
    
    homePageModel = homePageModelArrayList.get(position);

    holder.tv_name.setText(homePageModel.getFirstname()+" "+homePageModel.getLastname());
    holder.tv_designation.setText(homePageModel.getDesignation());
    

    
    byte[] image_str = dbHelper.readDataIcon(homePageModel.getFirstname());
    System.out.println("image_str = " + image_str);
    if (image_str != null)
    {
        Bitmap b = BitmapFactory.decodeByteArray(image_str, 0, image_str.length);
        holder.img_profile.setImageBitmap(Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true));
    
    }
    
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("f_name", homePageModel.getFirstname());
            context.startActivity(intent);
            
           // itemOnClickListener.onItemClick(view, position);
        }
    });
    
    
}


@Override
public int getItemCount() {
    return homePageModelArrayList.size();
}


public class HomePageViewHolder extends RecyclerView.ViewHolder {
    
    CircleImageView img_profile;
    AppCompatTextView tv_name, tv_designation;
    
    
    public HomePageViewHolder(@NonNull View itemView) {
        super(itemView);
    
        img_profile = itemView.findViewById(R.id.img_profilepic);
        tv_name = itemView.findViewById(R.id.name);
        tv_designation = itemView.findViewById(R.id.designation);
        
    }
    
}

}
