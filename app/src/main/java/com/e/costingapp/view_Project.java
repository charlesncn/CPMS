package com.e.costingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class view_Project extends AppCompatActivity {
    TextView tv_id, tv_location, tv_Sdate, tv_Edate, tv_Ecost, tv_Ccost, tv_pjName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);
        tv_id = findViewById(R.id.tv_id);
        tv_location = findViewById(R.id.tv_location);
        tv_Sdate = findViewById(R.id.tv_s_date);
        tv_Edate = findViewById(R.id.tv_e_date);
        tv_Ecost = findViewById(R.id.tv_e_cost);
        tv_Ccost = findViewById(R.id.tv_e_cost);
        tv_pjName = findViewById(R.id.tv_pjname);
    }
}