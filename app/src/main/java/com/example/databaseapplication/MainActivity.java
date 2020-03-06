package com.example.databaseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button RunQuery = findViewById(R.id.btnRunQuery);
        Button RunUpdate = findViewById(R.id.btnRunUpdate);
        final TextView txtOutput=findViewById(R.id.txtOutput);
        final EditText edtInput = findViewById(R.id.edtInput);
        RunQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext(),"DatabaseApp");
                String sql= edtInput.getText().toString().trim();
                Cursor cursor = databaseHelper.doQuery(sql);
                String output="";
                if(cursor==null) output="Table does not exist!";
                else {
                    while (cursor.moveToNext()) {
                        if (output.equals("")) output = cursor.getString(0);
                        else output += "\n" + cursor.getString(0);
                    }
                }
                txtOutput.setText(output);
            }
        });

        RunUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext(),"DatabaseApp");
                String sql= edtInput.getText().toString().trim();
                databaseHelper.doUpdate(sql);
            }
        });
    }
}
