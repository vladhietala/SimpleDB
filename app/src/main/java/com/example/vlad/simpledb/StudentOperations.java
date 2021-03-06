package com.example.vlad.simpledb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 11/05/2016.
 */
public class StudentOperations {

    private SimpleDBWrapper dbHelper;
    private String[] STUDENT_TABLE_COLUMS = {SimpleDBWrapper.STUDENT_ID, SimpleDBWrapper.STUDENT_NAME};
    private SQLiteDatabase database;

    public StudentOperations(Context context) {
        dbHelper = new SimpleDBWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public Student addStudent(String name) {
        ContentValues values = new ContentValues();

        values.put(SimpleDBWrapper.STUDENT_NAME, name);

        long studId = database.insert(SimpleDBWrapper.STUDENTS, null, values);

        Cursor cursor = database.query(SimpleDBWrapper.STUDENTS,
                STUDENT_TABLE_COLUMS, SimpleDBWrapper.STUDENT_ID + " = "
                        + studId, null, null, null, null);
        cursor.moveToFirst();

        Student newComment = parseStudent(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteStudent(Student comment) {
        long id = comment.getId();
        System.out.println("Removido id: " + id);
        database.delete(SimpleDBWrapper.STUDENTS, SimpleDBWrapper.STUDENT_ID
                + " = " + id, null);
    }

    public List getAllStudents() {
        List students = new ArrayList();

        Cursor cursor = database.query(SimpleDBWrapper.STUDENTS,
                STUDENT_TABLE_COLUMS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Student student = parseStudent(cursor);
            students.add(student);
            cursor.moveToNext();
        }

        cursor.close();
        return students;
    }

    private Student parseStudent(Cursor cursor) {
        Student student = new Student();
        student.setId(cursor.getInt(0));
        student.setName(cursor.getString(1));
        return student;
    }
}