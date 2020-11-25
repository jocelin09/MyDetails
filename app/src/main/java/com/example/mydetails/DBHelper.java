package com.example.mydetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
// Database Version
private static final int DATABASE_VERSION = 1;

// Database Name
private static final String DATABASE_NAME = "details.db";


public DBHelper(@Nullable Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}


@Override
public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS my_details_data(ID INTEGER PRIMARY KEY,FirstName TEXT,LastName TEXT,PhoneNumber TEXT,Gender TEXT, DateofBirth TEXT, EmployeeNo TEXT, EmployeeName TEXT, Designation TEXT, AccountType TEXT, WorkExperience TEXT, ProfileImage BLOB, BankName TEXT, BranchName TEXT, AccountNo TEXT, IFSCode TEXT)");
    //                                                                          0                   1               2           3               4               5               6                   7                   8               9                   10                    11              12             13              14              15
    
}

@Override
public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS my_details_data");
    onCreate(sqLiteDatabase);
    
}

//COUNT QUERy
public int countTable(String tablenm, String first_name) {
    SQLiteDatabase db1 = this.getWritableDatabase();
    Cursor cursor1 = db1.rawQuery("Select count(*) from " + tablenm + " where FirstName = '"+first_name+"' ;", null);
    cursor1.moveToFirst();
    int count = cursor1.getInt(0);
    cursor1.close();
    return count;
}

public boolean insertDetails(String FirstName, String LastName, String PhoneNumber,String Gender, String DateofBirth,String EmployeeNo,
                                 String EmployeeName, String Designation,String AccountType, String WorkExperience, byte[]  ProfileImage, String BankName, String BranchName, String AccountNo, String IFSCode)
{
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    
    values.put("FirstName", FirstName);
    values.put("LastName", LastName);
    values.put("PhoneNumber", PhoneNumber);
    values.put("Gender", Gender);
    values.put("DateofBirth", DateofBirth);
    values.put("EmployeeNo", EmployeeNo);
    values.put("EmployeeName", EmployeeName);
    values.put("Designation", Designation);
    values.put("AccountType", AccountType);
    values.put("WorkExperience", WorkExperience);
    values.put("ProfileImage", ProfileImage);
    values.put("BankName", BankName);
    values.put("BranchName", BranchName);
    values.put("AccountNo", AccountNo);
    values.put("IFSCode", IFSCode);
    
    long result = db.insert("my_details_data", null, values);
    
    if (result == -1)
        return false;
    else
        return true;
    
}
public boolean updateDetails(String FirstName, String LastName, String PhoneNumber,String Gender, String DateofBirth,String EmployeeNo,
                                 String EmployeeName, String Designation,String AccountType, String WorkExperience, byte[] ProfileImage,
                             String BankName, String BranchName, String AccountNo, String IFSCode)
{
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    
    values.put("FirstName", FirstName);
    values.put("LastName", LastName);
    values.put("PhoneNumber", PhoneNumber);
    values.put("Gender", Gender);
    values.put("DateofBirth", DateofBirth);
    values.put("EmployeeNo", EmployeeNo);
    values.put("EmployeeName", EmployeeName);
    values.put("Designation", Designation);
    values.put("AccountType", AccountType);
    values.put("WorkExperience", WorkExperience);
    values.put("ProfileImage", ProfileImage);
    values.put("BankName", BankName);
    values.put("BranchName", BranchName);
    values.put("AccountNo", AccountNo);
    values.put("IFSCode", IFSCode);
    
    long result = db.update("my_details_data", values,"FirstName ='" + FirstName + "'", null);
    
    if (result == -1)
        return false;
    else
        return true;
    
}

public byte[] readDataIcon(String FirstName) {
    
    byte[] bdata = null;
    String Sqlstring = "Select ProfileImage from my_details_data Where FirstName='" + FirstName + "'";
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(Sqlstring, null);
    
    if (cursor.moveToFirst()) {
        do {
            bdata = cursor.getBlob(0);
        } while (cursor.moveToNext());
    }
    
    cursor.close();
    db.close();
    
    return bdata;
}



}
