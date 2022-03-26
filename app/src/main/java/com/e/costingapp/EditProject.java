package com.e.costingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditProject extends AppCompatActivity {

    TextView disclaimer;
    LinearLayout back_icon, ll_update;
    TextInputEditText pj_id, pj_name, s_date, e_date, pj_location, pj_cost;
    String id, name, start_d, end_d, loci, cost;
    Button delete, add;
    TextView date_today;
    DigitalClock time;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);


//        finding views of items.
        disclaimer = findViewById(R.id.editing_panel);
        back_icon = findViewById(R.id.go_back);
        pj_id =findViewById(R.id.ed_id);
        pj_name =findViewById(R.id.ed_name);
        s_date = findViewById(R.id.ed_start_date);
        e_date =findViewById(R.id.ed_end_date);
        pj_location = findViewById(R.id.ed_location);
        pj_cost = findViewById(R.id.ed_esimate_cost);
        add =findViewById(R.id.update_pj);
        ll_update =findViewById(R.id.ll_update);
        date_today = findViewById(R.id.tv_e_date);
        time = findViewById(R.id.ai_time);



//      getting data from ViewProjectActivity.
        Intent intent = getIntent();
        String result_name = intent.getStringExtra("name");
        String result_id = intent.getStringExtra("id");
        String result_loci = intent.getStringExtra("loci");
        String result_s_date = intent.getStringExtra("start_d");
        String result_e_date = intent.getStringExtra("end_d");
        String result_est_cost = intent.getStringExtra("cost");
//        int id_to_int = Integer.parseInt(result_id);
        pj_name.setText(result_name);
        pj_id.setText(result_id);
        pj_location.setText(result_loci);
        s_date.setText(result_s_date);
        e_date.setText(result_e_date);
        pj_cost.setText(result_est_cost);

        getAndSetIntentData();

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = new MainActivity();
                mainActivity.finish();
                Intent intent = new Intent(EditProject.this, Project_Home.class);
                startActivity(intent);
                finish();
            }
        });
////        getting time and date
//        String today_date = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
//        String time_now = new SimpleDateFormat("hh:mm:ss z", Locale.getDefault()).format(new Date());
////        set date
//        date_today.setText(today_date);
//        time.setText(time_now);

//      open main activity
        ll_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(ViewProjectActivity.this, MainActivity.class);
//                startActivity(intent);
                Intent i=new Intent(EditProject.this, MainActivity.class);
                String temp_name = pj_name.getText().toString();
                String temp_id = pj_id.getText().toString();
                String temp_loci = pj_location.getText().toString();
                String temp_s_date = s_date.getText().toString();
                String temp_e_date = e_date.getText().toString();
                String temp_e_cost = pj_cost.getText().toString();
                i.putExtra("name",temp_name);
                i.putExtra("id",temp_id);
                i.putExtra("start_d",temp_s_date);
                i.putExtra("end_d",temp_e_date);
                i.putExtra("loci",temp_loci);
                i.putExtra("cost",temp_e_cost);

                startActivity(i);
                finish();
            }
        });



    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") || getIntent().hasExtra("name") || getIntent().hasExtra("start_d")
                || getIntent().hasExtra("end_d") || getIntent().hasExtra("loci") || getIntent().hasExtra("cost")) {

//          getting data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            start_d = getIntent().getStringExtra("start_d");
            end_d = getIntent().getStringExtra("end_d");
            loci = getIntent().getStringExtra("loci");
            cost = getIntent().getStringExtra("cost");

//            setting data to the intent
            pj_id.setText(id);
            pj_name.setText(name);
            s_date.setText(start_d);
            e_date.setText(end_d);
            pj_location.setText(loci);
            pj_cost.setText(cost);

        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}