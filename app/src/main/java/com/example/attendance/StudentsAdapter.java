package com.example.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class StudentsAdapter extends BaseAdapter {

    List<Student> students;
    Context context;

    public StudentsAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View v = layoutInflater.inflate(R.layout.student_list_view_adapter_layout, viewGroup,false);

        TextView id = v.findViewById(R.id.std_id);
        TextView name = v.findViewById(R.id.std_name);

        id.setText(students.get(i).getId());
        name.setText(students.get(i).getName());

        return v;
    }
}
