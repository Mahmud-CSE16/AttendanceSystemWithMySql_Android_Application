package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    EditText editTextName,editTextStdId;
    Spinner spinnerDepts,spinnerSections;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        ViewById();

        progressDialog = new ProgressDialog(this);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.depts, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepts.setAdapter(adapter1);

        progressDialog = new ProgressDialog(this);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.sections, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSections.setAdapter(adapter2);
    }

    public void addStudent(View view){

        if(spinnerDepts.getSelectedItem().toString().equals("CSE/BBA/SWE") || spinnerSections.getSelectedItem().toString().equals("A/B/C/D/E") ){
            Toast.makeText(this,"Please Choose Depertment and Sections",Toast.LENGTH_SHORT).show();
        }else{
            insideAddStudent();
        }
    }

    private void insideAddStudent(){
        final String name = editTextName.getText().toString().trim();
        final String std_id = editTextStdId.getText().toString().trim();
        final String dept = spinnerDepts.getSelectedItem().toString();
        final String section = spinnerSections.getSelectedItem().toString();

        progressDialog.setMessage("Add Studnet...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_STUDENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("std_id",std_id);
                params.put("dept",dept);
                params.put("section",section);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void ViewById(){
        editTextName = findViewById(R.id.editText_name);
        editTextStdId = findViewById(R.id.editText_std_id);
        spinnerDepts = findViewById(R.id.spinner_depts);
        spinnerSections = findViewById(R.id.spinner_sections);
    }
}
