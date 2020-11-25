package com.example.mydetails.fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.example.mydetails.DBHelper;
import com.example.mydetails.R;
import com.example.mydetails.databinding.PersonalInfoFragBinding;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Personal_Info_Fragment extends Fragment {

    PersonalInfoFragBinding personalInfoFragBinding;
    String str_f_name = "", str_l_name ="", str_phonenum ="", str_gender="", str_dateofbirth ="";
    private int mYear, mMonth, mDay;
String first_name_intent="";
DBHelper dbHelper;
SQLiteDatabase sqLiteDatabase;

@Override
public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
) {
    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Personal Details");
    
    personalInfoFragBinding = DataBindingUtil.inflate(
            inflater, R.layout.personal_info_frag, container, false);
    View view = personalInfoFragBinding.getRoot();
    
    dbHelper = new DBHelper(getActivity());
    sqLiteDatabase = dbHelper.getWritableDatabase();
    
    
    first_name_intent= getArguments().getString("f_name");
    System.out.println("first_name_intent = " + first_name_intent);
    
    setData();
    
    personalInfoFragBinding.imgCal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
    
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
    
    
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                    
                            DecimalFormat mFormat= new DecimalFormat("00");
                            mFormat.format(Double.valueOf(monthOfYear));
                    
                            mFormat.setRoundingMode(RoundingMode.DOWN);
                            String selected_date =  mFormat.format(Double.valueOf(dayOfMonth)) + "/" +  mFormat.format(Double.valueOf(monthOfYear+1)) + "/" +  mFormat.format(Double.valueOf(year));
                            personalInfoFragBinding.edtDob.setText(selected_date);
                    
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
            
        }
    });
    // Inflate the layout for this fragment
    return view;
}

private void setData() {
    String radioValue = "";
    
    String sql = "select * from my_details_data where FirstName = '"+first_name_intent+"'";
    Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
    if(cursor.moveToFirst()){
        do{
            
            String f_name = cursor.getString(1);
            String l_name = cursor.getString(2);
            String phonenum = cursor.getString(3);
            String gender = cursor.getString(4);
            String dob = cursor.getString(5);
    
            System.out.println("gender = " + gender);
            
            
            personalInfoFragBinding.fName.setText(f_name);
            personalInfoFragBinding.fName.setEnabled(false);
            personalInfoFragBinding.lName.setText(l_name);
            personalInfoFragBinding.edtPhNum.setText(phonenum);
            
            for (int i = 0; i < personalInfoFragBinding.radioGender.getChildCount(); i++) {
                RadioButton btn = (RadioButton) personalInfoFragBinding.radioGender.getChildAt(i);
        
                if (btn.getText().equals("Male") == true) {
                    radioValue = "gender";
                } else if (btn.getText().equals("Female") == true) {
                    radioValue = "Female";
                } else if (btn.getText().equals("Others") == true) {
                    radioValue = "Others";
                }
    
                System.out.println("btn = " + btn);
        
                if (gender.equals(radioValue)) {
                    btn.setChecked(true);
                } else if (gender.equals(radioValue)) {
                    btn.setChecked(true);
                } else if (gender.equals(radioValue)) {
                    btn.setChecked(true);
                }
        
            }
            personalInfoFragBinding.edtDob.setText(dob);
        }while (cursor.moveToNext());
    }
    cursor.close();

}

public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    personalInfoFragBinding.btnNextPersonal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            
            str_f_name = personalInfoFragBinding.fName.getText().toString();
         
    
            if (str_f_name.equals(""))
            {
                personalInfoFragBinding.fName.setError("Please enter First Name");
            }
            else
            {
    
                str_l_name = personalInfoFragBinding.lName.getText().toString();
                str_phonenum = personalInfoFragBinding.edtPhNum.getText().toString();
                str_dateofbirth = personalInfoFragBinding.edtDob.getText().toString();
    
                int selectedId = personalInfoFragBinding.radioGender.getCheckedRadioButtonId();
                System.out.println("selectedId = " + selectedId);
                if (selectedId != -1){
                    RadioButton radioButton = (RadioButton) personalInfoFragBinding.radioGender.findViewById(selectedId);
                    str_gender = (String) radioButton.getText();
                }
    
    
                Bundle bundle = new Bundle();
                bundle.putString("f_name", str_f_name);
                bundle.putString("l_name", str_l_name);
                bundle.putString("phone_num", str_phonenum);
                bundle.putString("gender", str_gender);
                bundle.putString("date_of_birth", str_dateofbirth);
                NavHostFragment.findNavController(Personal_Info_Fragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                
            }
           
    
        }
    });
    
    
}
}