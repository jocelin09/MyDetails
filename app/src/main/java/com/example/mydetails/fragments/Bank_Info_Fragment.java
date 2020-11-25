package com.example.mydetails.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mydetails.DBHelper;
import com.example.mydetails.homepage.HomePageActivity;
import com.example.mydetails.R;
import com.example.mydetails.databinding.BankInfoFragBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class Bank_Info_Fragment extends Fragment {

    BankInfoFragBinding bankInfoFragBinding;
String str_f_name="",str_l_name="",str_phonenum="",str_gender="",str_dateofbirth="",str_empno="",str_empname="",str_designation="",
        str_account_type="",str_workexp="",str_bank_name="",str_branch_name="",str_account_no="",str_ifsc="";

ArrayList<String> branchNameArrayList = new ArrayList<String>();
DBHelper dbHelper;
SQLiteDatabase sqLiteDatabase;
int tableCount;
private static final int REQUEST_CAMERA = 123;
private static final int STORAGE_PERMISSION_CODE = 101;
byte[] byteArray;


@Override
public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
) {
    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bank Details");
    
    bankInfoFragBinding = DataBindingUtil.inflate(
            inflater, R.layout.bank_info_frag, container, false);
    View view = bankInfoFragBinding.getRoot();
    
    dbHelper = new DBHelper(getContext());
    sqLiteDatabase = dbHelper.getWritableDatabase();
    
    str_f_name = getArguments().getString("f_name");
    str_l_name = getArguments().getString("l_name");
    str_phonenum = getArguments().getString("phone_num");
    str_gender = getArguments().getString("gender");
    str_dateofbirth = getArguments().getString("date_of_birth");
    str_empno = getArguments().getString("emp_no");
    str_empname = getArguments().getString("emp_name");
    str_designation = getArguments().getString("designation");
    str_account_type = getArguments().getString("account_type");
    str_workexp = getArguments().getString("work_exp");
    
    branchNameArrayList.clear();
    branchNameArrayList.add("Select Branch Name");
    branchNameArrayList.add("Branch name 1");
    branchNameArrayList.add("Branch name 2");
    branchNameArrayList.add("Branch name 3");
    
    ArrayAdapter branch_adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, branchNameArrayList);
    bankInfoFragBinding.branchName.setAdapter(branch_adapter);
    bankInfoFragBinding.branchName.setSelection(0);
    branch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
    bankInfoFragBinding.btnAddimage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isReadStorageAllowed())
            {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, REQUEST_CAMERA);
            }
            else {
                requestStoragePermission();
            }
            
        
        }
    });
    
    setData();
    
    // Inflate the layout for this fragment
    return view;
}

private void setData() {
    
    String sql = "select * from my_details_data where FirstName = '"+str_f_name+"'";
    Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
    if(cursor.moveToFirst()){
        do{
            
            byte[] profile = cursor.getBlob(11);
            String bankname = cursor.getString(12);
            String branchname = cursor.getString(13);
            String acc_no = cursor.getString(14);
            String ifsc = cursor.getString(14);
            
            bankInfoFragBinding.bankName.setText(bankname);
            bankInfoFragBinding.edtAccNo.setText(acc_no);
            bankInfoFragBinding.edtIfsc.setText(ifsc);
            
            ArrayAdapter myAdap = (ArrayAdapter) bankInfoFragBinding.branchName.getAdapter();
            int spinnerPosition = myAdap.getPosition(branchname);
            bankInfoFragBinding.branchName.setSelection(spinnerPosition);
    
           
            if (profile != null)
            {
                Bitmap b = BitmapFactory.decodeByteArray(profile, 0, profile.length);
                bankInfoFragBinding.profileImage.setImageBitmap(Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true));
        
            }
            
        }while (cursor.moveToNext());
    }
    cursor.close();
    
}


public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    bankInfoFragBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          
           str_bank_name = bankInfoFragBinding.bankName.getText().toString();
           str_branch_name = bankInfoFragBinding.branchName.getSelectedItem().toString();
           str_account_no = bankInfoFragBinding.edtAccNo.getText().toString();
           str_ifsc = bankInfoFragBinding.edtIfsc.getText().toString();
           
           tableCount = dbHelper.countTable("my_details_data", str_f_name);
           
           
           if (tableCount == 0)
           {
               Toast.makeText(getActivity(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
               dbHelper.insertDetails(str_f_name,str_l_name,str_phonenum,str_gender,str_dateofbirth,str_empno,str_empname,str_designation,str_account_type,str_workexp,byteArray,str_bank_name,str_branch_name,str_account_no,str_ifsc);
           }
           else
           {
               Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
               dbHelper.updateDetails(str_f_name,str_l_name,str_phonenum,str_gender,str_dateofbirth,str_empno,str_empname,str_designation,str_account_type,str_workexp,byteArray,str_bank_name,str_branch_name,str_account_no,str_ifsc);
           }
           
           startActivity(new Intent(getActivity(), HomePageActivity.class));
    
            
        }
    });
}

@Override
public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        bankInfoFragBinding.profileImage.setImageBitmap(photo);
    
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
        
        System.out.println(byteArray);
        
        
    }
    
    
}

private void requestStoragePermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)
        && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
    
    // if
    // (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
    {
    
    }
    
    // And finally ask for the permission
    ActivityCompat.requestPermissions(
            getActivity(), new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
            STORAGE_PERMISSION_CODE);
}

private boolean isReadStorageAllowed() {
    // Getting the permission mainitem_status
    int result = 0;
    try {
        
        // ContextCompat.checkSelfPermission(this,
        // Manifest.permission.READ_EXTERNAL_STORAGE);
        result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                         + ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                         + ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
    } catch (Exception e) {
        
        // If permission is granted returning true
    }
    if (result == PackageManager.PERMISSION_GRANTED)
        return true;
    
    // If permission is not granted returning false
    return false;
}

public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                       @NonNull int[] grantResults) {
    
    // Checking the request code of our request
    if (requestCode == STORAGE_PERMISSION_CODE) {
        
        // If permission is granted
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            
            try {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, REQUEST_CAMERA);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("e = " + e);
                
            }
        } else {
            // Displaying another toast if permission is not granted
            Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
        }
    }
}

}