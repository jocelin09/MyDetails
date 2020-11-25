package com.example.mydetails.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mydetails.DBHelper;
import com.example.mydetails.R;
import com.example.mydetails.databinding.EmployeeInfoFragBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Employee_Info_Fragment extends Fragment {

    EmployeeInfoFragBinding employeeInfoFragBinding;
    
    String str_f_name="",str_l_name="",str_phonenum="",str_gender="",str_dateofbirth="",str_empno="",str_empname="",str_designation="",
            str_account_type="",str_workexp="";
    
    ArrayList<String> accountTypeArrayList = new ArrayList<String>();
    ArrayList<String> workexpArrayList = new ArrayList<String>();
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    
    
@Override
public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
) {
    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Employee Details");
    
    employeeInfoFragBinding = DataBindingUtil.inflate(
            inflater, R.layout.employee_info_frag, container, false);
    View view = employeeInfoFragBinding.getRoot();
    
    dbHelper = new DBHelper(getActivity());
    sqLiteDatabase = dbHelper.getWritableDatabase();
    
    str_f_name = getArguments().getString("f_name");
    str_l_name = getArguments().getString("l_name");
    str_phonenum = getArguments().getString("phone_num");
    str_gender = getArguments().getString("gender");
    str_dateofbirth = getArguments().getString("date_of_birth");
    
    
    System.out.println("str_f_name = " + str_f_name);
    
    accountTypeArrayList.clear();
    workexpArrayList.clear();
    
    accountTypeArrayList.add("Select Account Type");
    accountTypeArrayList.add("Account Type 1");
    accountTypeArrayList.add("Account Type 2");
    accountTypeArrayList.add("Account Type 3");
    
    ArrayAdapter acc_type_adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, accountTypeArrayList);
    employeeInfoFragBinding.accountType.setAdapter(acc_type_adapter);
    employeeInfoFragBinding.accountType.setSelection(0);
    acc_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
    
    workexpArrayList.add("Select Work Experience");
    workexpArrayList.add("0-3 years");
    workexpArrayList.add("3-5 years");
    workexpArrayList.add("5-8 years");
    workexpArrayList.add("8+ years");
    
    ArrayAdapter work_exp_adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, workexpArrayList);
    employeeInfoFragBinding.workExp.setAdapter(work_exp_adapter);
    employeeInfoFragBinding.workExp.setSelection(0);
    work_exp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
    
    setData();
    // Inflate the layout for this fragment
    return view;
}

private void setData() {
    
    String sql = "select * from my_details_data where FirstName = '"+str_f_name+"'";
    Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
    if(cursor.moveToFirst()){
        do{
            
            String emp_no = cursor.getString(6);
            String emp_name = cursor.getString(7);
            String emp_desg = cursor.getString(8);
            String emp_acctype = cursor.getString(9);
            String emp_workexp = cursor.getString(10);
            
            
            
            employeeInfoFragBinding.edtEmpNo.setText(emp_no);
            employeeInfoFragBinding.edtEmpName.setText(emp_name);
            employeeInfoFragBinding.edtEmpDsg.setText(emp_desg);
    
            ArrayAdapter myAdap = (ArrayAdapter) employeeInfoFragBinding.accountType.getAdapter();
            int spinnerPosition = myAdap.getPosition(emp_acctype);
            employeeInfoFragBinding.accountType.setSelection(spinnerPosition);
    
            ArrayAdapter myAdap2 = (ArrayAdapter) employeeInfoFragBinding.workExp.getAdapter();
            int spinnerPosition2 = myAdap2.getPosition(emp_workexp);
            employeeInfoFragBinding.workExp.setSelection(spinnerPosition2);
            
        }while (cursor.moveToNext());
    }
    cursor.close();
    
}


public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    employeeInfoFragBinding.btnNextEmployee.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            str_empno = employeeInfoFragBinding.edtEmpNo.getText().toString();
            str_empname = employeeInfoFragBinding.edtEmpName.getText().toString();
            str_designation = employeeInfoFragBinding.edtEmpDsg.getText().toString();
            str_account_type = employeeInfoFragBinding.accountType.getSelectedItem().toString();
            str_workexp = employeeInfoFragBinding.workExp.getSelectedItem().toString();
    
            Bundle bundle = new Bundle();
            bundle.putString("f_name", str_f_name);
            bundle.putString("l_name", str_l_name);
            bundle.putString("phone_num", str_phonenum);
            bundle.putString("gender", str_gender);
            bundle.putString("date_of_birth", str_dateofbirth);
            bundle.putString("emp_no", str_empno);
            bundle.putString("emp_name", str_empname);
            bundle.putString("designation", str_designation);
            bundle.putString("account_type", str_account_type);
            bundle.putString("work_exp", str_workexp);
            
            NavHostFragment.findNavController(Employee_Info_Fragment.this)
                    .navigate(R.id.action_SecondFragment_to_ThirdFrgment, bundle);
        }
    });
}
}