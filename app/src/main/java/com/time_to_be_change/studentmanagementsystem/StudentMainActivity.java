package com.time_to_be_change.studentmanagementsystem;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.app.AlertDialog.Builder;
import android.view.View.OnClickListener;
import android.widget.Spinner;


public class StudentMainActivity extends AppCompatActivity{

    EditText ename,eroll_no,emarks;
    Spinner spin;

    Button add,delete,modify,view,viewall,show1;
    SQLiteDatabase db;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        ename = (EditText) findViewById(R.id.name);
        eroll_no = (EditText) findViewById(R.id.roll_no);
//        spin = (Spinner) findViewById(R.id.roll_no);
        emarks = (EditText) findViewById(R.id.marks);

        add =(Button) findViewById(R.id.addbtn);
        delete =(Button) findViewById(R.id.deletebtn);
        modify =(Button) findViewById(R.id.modifybtn);
        view =(Button) findViewById(R.id.viewbtn);
        viewall =(Button) findViewById(R.id.viewallbtn);
        show1 =(Button) findViewById(R.id.showbtn);


            db=openOrCreateDatabase("student_manage",Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS student(roll_no INTEGER,name VARCHAR,marks INTEGER);");




        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (eroll_no.getText().toString().trim().length()==0||ename.getText().toString().length()==0||emarks.getText().toString().length()==0)
                {
                    showMessage("ERROR", "please enter all values");
                    return;
                }

                db.execSQL("INSERT INTO student VALUES('"+eroll_no.getText().toString()+"','"+ename.getText()+"','"+emarks.getText()+"');");
                showMessage("Success","Record added successfully");
                clearText();
            }
        });


        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter subject");
                    return;
                }

                Cursor c=db.rawQuery("SELECT * FROM student WHERE roll_no='"+eroll_no.getText()+"'",null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM student WHERE roll_no='"+eroll_no.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid subject");
                }
                clearText();
            }
        });

        modify.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter subject");
                    return;
                }
                else{
                Cursor c=db.rawQuery("SELECT * FROM student WHERE roll_no='"+eroll_no.getText()+"'", null);

                    if (c.moveToFirst()) {
                        db.execSQL("UPDATE student SET name='" + ename.getText() + "',marks='" + emarks.getText() +
                                "' WHERE roll_no='" + eroll_no.getText() + "'");
                        showMessage("Success", "Record Modified");
                    } else {
                        showMessage("Error", "Invalid subject");
                    }
                }
                clearText();
            }
        });
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {



                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Roll No");
                    return;
                }

                Cursor c =db.rawQuery("SELECT * FROM student WHERE roll_no='"+eroll_no.getText()+"'", null);
                if(c.moveToNext())
                {
                    ename.setText(c.getString(1));
                    emarks.setText(c.getString(2));

                }
                else
                {
                    showMessage("Error", "Invalid subject");
                    clearText();
                }



            }
        });

        viewall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c=db.rawQuery("SELECT * FROM student", null);
                if(c.getCount()==0)

                    if(c.getCount()==0)
                    {
                        showMessage("Error", "No records found");
                        return;
                    }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Rollno: ").append(c.getString(0)).append("\n");
                    buffer.append("Name: ").append(c.getString(1)).append("\n");
                    buffer.append("Marks: ").append(c.getString(2)).append("\n\n");
                }
                showMessage("Student Details", buffer.toString());
            }
        });

        show1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage("Student Management Application", "Developed By Balkrishna shah ");
            }
        });
    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        eroll_no.setText("");
        ename.setText("");
        emarks.setText("");
        eroll_no.requestFocus();
    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        String spinitem = parent.getItemAtPosition(position).toString();
//    }
}








