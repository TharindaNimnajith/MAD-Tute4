package com.example.tute4_2;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Database.DBHelper;

public class MainActivity extends AppCompatActivity {

    Button btn_selectall, btn_signin, btn_add, btn_update, btn_delete;
    EditText et_uname, et_pwd;

    DBHelper dbHelper;
    ArrayList usernames, passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_selectall = findViewById(R.id.btn_selectAll);
        btn_add = findViewById(R.id.btn_add);
        btn_signin = findViewById(R.id.btn_signIn);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        et_uname = findViewById(R.id.et_username);
        et_pwd = findViewById(R.id.et_password);

        dbHelper = new DBHelper(this);

        usernames = new ArrayList();
        passwords = new ArrayList();

        btn_selectall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dbHelper.selectall();

                if (cursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No data to show", Toast.LENGTH_SHORT).show();
                } else {
                    if (cursor.moveToFirst()) {
                        usernames.add(cursor.getString(1));
                        passwords.add(cursor.getString(2));

                        while (cursor.moveToNext()) {
                            usernames.add(cursor.getString(1));
                            passwords.add(cursor.getString(2));
                        }
                    }
                }

                int i = 0;

                while (i < cursor.getCount()) {
                    Log.d("details", "" + usernames.get(i) + " " + passwords.get(i));
                    i++;
                }

            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = et_uname.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();

                boolean x = dbHelper.signin(uname, pwd);

                if (x) {
                    clearData();
                    Toast.makeText(MainActivity.this, "Successfully signed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = et_uname.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();

                dbHelper.add(uname, pwd);

                clearData();
                Toast.makeText(MainActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = et_uname.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();

                dbHelper.update(uname, pwd);

                clearData();
                Toast.makeText(MainActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = et_uname.getText().toString().trim();

                dbHelper.delete(uname);

                clearData();
                Toast.makeText(MainActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clearData() {
        et_uname.setText("");
        et_pwd.setText("");
    }
}
