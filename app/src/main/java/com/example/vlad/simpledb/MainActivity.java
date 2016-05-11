package com.example.vlad.simpledb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list;
    private StudentOperations studentDBoperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentDBoperations = new StudentOperations(this);
        try {
            studentDBoperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List values = studentDBoperations.getAllStudents();

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
    }

    public void addUser(View view) {
        ArrayAdapter adapter = (ArrayAdapter) list.getAdapter();

        EditText text = (EditText) findViewById(R.id.input);
        Student stud = studentDBoperations.addStudent(text.getText().toString());
        adapter.add(stud);
    }

    public void deleteFirstUser(View view) {
        ArrayAdapter adapter = (ArrayAdapter) list.getAdapter();
        Student stud = null;

        if (((ArrayAdapter) list.getAdapter()).getCount() > 0) {
            stud = (Student) list.getAdapter().getItem(0);
            studentDBoperations.deleteStudent(stud);
            adapter.remove(stud);
        }
    }

    @Override
    protected void onResume() {
        try {
            studentDBoperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        studentDBoperations.close();
        super.onPause();
    }
}
