package com.example.tasky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class tasksadd extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText editText3;
    Button button;
    String hrs;
    String mins;
    String task;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasksadd);

        editText1 = findViewById(R.id.edittext1);
        editText2 = findViewById(R.id.edittext2);
        editText2.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        try {
                            String input = dest.toString() + source.toString();
                            int value = Integer.parseInt(input);
                            if (value < 0 || value > 59) {
                                return "";
                            }
                        } catch (NumberFormatException e) {
                            return "";
                        }
                        return null;
                    }



                }
        });
        editText1.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        try {
                            String input = dest.toString() + source.toString();
                            int value = Integer.parseInt(input);
                            if (value < 0 || value > 9) {
                                return "";
                            }
                        } catch (NumberFormatException e) {
                            return "";
                        }
                        return null;
                    }

                }
        });
     editText3 = findViewById(R.id.edittext3);
        button = findViewById(R.id.button3);
        DatabaseHelper database = new DatabaseHelper(tasksadd.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hrs = editText1.getText().toString();
                mins = editText2.getText().toString();
                task = editText3.getText().toString();
                if(!hrs.isEmpty() && !mins.isEmpty() && !task.isEmpty()){

                database.addContact(hrs+":"+mins+":"+0,task);
                Intent iprev = new Intent(tasksadd.this, MainActivity.class);

                startActivity(iprev);
            }
            else {
                    Toast.makeText(tasksadd.this, "all fields are not completely filled", Toast.LENGTH_SHORT).show();}
            }

        });

    }
}









