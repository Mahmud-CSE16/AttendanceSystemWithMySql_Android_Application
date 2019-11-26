package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAttendanceActivity extends AppCompatActivity {

    Spinner spinnerCourses,spinnerSections;

    private ProgressDialog progressDialog;

    TextView textViewDate;
    Button buttonDate;
    ListView listView;

    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        ViewById();

        progressDialog = new ProgressDialog(this);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.cources, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapter1);

        progressDialog = new ProgressDialog(this);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.sections, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSections.setAdapter(adapter2);

        getStudents();
    }


    private void getStudents(){

        progressDialog.setMessage("Load Students...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_STUDENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();


                        try {
                            List<Student> students = new ArrayList<Student>();

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonarray = (JSONArray) jsonObject.get("students");
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String name = jsonobject.getString("name");
                                String id = jsonobject.getString("std_id");
                                String dept = jsonobject.getString("dept");
                                String section = jsonobject.getString("section");

                                Student student = new Student(id,name,dept,section);
                                students.add(student);
                            }

                            StudentsAdapter studentsAdapter = new StudentsAdapter(students,getApplicationContext());
                            listView.setAdapter(studentsAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void save(View view){
        Toast.makeText(getApplicationContext(),"Submit Successfully",Toast.LENGTH_LONG).show();
    }

    public void datepicker(View view){
        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                textViewDate.setText(i2+"/"+i1+"/"+i);
            }
        },day,month,year);
        dpd.show();
    }

    private void ViewById(){
        spinnerCourses = findViewById(R.id.spinner_courses);
        spinnerSections = findViewById(R.id.spinner_sections);
        textViewDate = findViewById(R.id.date_picker_textView);
        buttonDate = findViewById(R.id.date_picker);
        listView = findViewById(R.id.listView);
    }
}
