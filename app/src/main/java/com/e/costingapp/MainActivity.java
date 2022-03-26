package com.e.costingapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //    FloatingActionButton addbutton;
//    private int STORAGE_PERMISSION_CODE= 1;
    MyDatabaseHelper1 myDB;
    ArrayList<String> item_id, item_name, item_cost, item_qtt, item_desc, item_date, item_supplier;
    CustomAdapter customAdapter;
    FrameLayout frameLayout;
//    Fragment fragment=null;
//    FragmentTransaction ft;
//    FragmentManager fm;
    FloatingActionButton addbutton;
    TextView pj_name, pj_id, location, s_date, e_date, e_cost, c_cost;
    LinearLayout back;
    LinearLayout lledit,llanalysis;

//  hold intent data.
    String id, name, start_d, end_d, loci, cost;

    private int STORAGE_PERMISSION_CODE= 1;
//    MenuItem reload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        frameLayout=findViewById(R.id.home_frame_layout);
        addbutton = findViewById(R.id.actionBtn);


        pj_name = findViewById(R.id.tv_pjname);
        pj_id = findViewById(R.id.tv_pj_id);
        back = findViewById(R.id.go_back);
        location =findViewById(R.id.tv_location);
        s_date =findViewById(R.id.tv_s_date);
        e_date =findViewById(R.id.tv_e_date);
        e_cost =findViewById(R.id.tv_e_cost);
        c_cost =findViewById(R.id.tv_c_cost);

        lledit = findViewById(R.id.lledit);
        llanalysis = findViewById(R.id.llanalysis);

        recyclerView = findViewById(R.id.recyclerview1);







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
        location.setText(result_loci);
        s_date.setText(result_s_date);
        e_date.setText(result_e_date);
        e_cost.setText(result_est_cost);

        getAndSetIntentData();






        myDB =new MyDatabaseHelper1(MainActivity.this);
        item_id =new ArrayList<>();
        item_name =new ArrayList<>();
        item_cost =new ArrayList<>();
        item_qtt =new ArrayList<>();
        item_desc =new ArrayList<>();
        item_date =new ArrayList<>();
        item_supplier =new ArrayList<>();



        customAdapter = new CustomAdapter(MainActivity.this, this, item_id, item_name, item_cost, item_qtt,
                item_desc, item_date, item_supplier);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



//      getting data from ViewProjectActivity.
        Intent new_Intent = getIntent();
        String new_Result_name = new_Intent.getStringExtra("name");
        String new_Result_id = new_Intent.getStringExtra("id");
        String new_Result_loci = new_Intent.getStringExtra("loci");
        String new_Result_s_date = new_Intent.getStringExtra("start_d");
        String new_Result_e_date = new_Intent.getStringExtra("end_d");
        String new_Result_est_cost = new_Intent.getStringExtra("cost");
//        int id_to_int = Integer.parseInt(result_id);
        pj_name.setText(new_Result_name);
        pj_id.setText(new_Result_id);
        location.setText(new_Result_loci);
        s_date.setText(new_Result_s_date);
        e_date.setText(new_Result_e_date);
        e_cost.setText(new_Result_est_cost);



        storeDataInArray();

//        go back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Project_Home.class);
                startActivity(intent);
                finish();
            }
        });

//        floating action button
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                    Intent i=new Intent(MainActivity.this, AddActivity.class);
                    String temp_id = pj_id.getText().toString();
                    i.putExtra("p_id",temp_id);

                    startActivity(i);


                }
                else{
                    requestStoragePermission();
                }

            }
        });

//        button to open edit project
        lledit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditProject.class);
                String temp_name = pj_name.getText().toString();
                String temp_id = pj_id.getText().toString();
                String temp_loci = location.getText().toString();
                String temp_s_date = s_date.getText().toString();
                String temp_e_date = e_date.getText().toString();
                String temp_e_cost = e_cost.getText().toString();
                i.putExtra("name",temp_name);
                i.putExtra("id",temp_id);
                i.putExtra("start_d",temp_s_date);
                i.putExtra("end_d",temp_e_date);
                i.putExtra("loci",temp_loci);
                i.putExtra("cost",temp_e_cost);
                startActivity(i);
            }
        });

//        button to open project analytics
        llanalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, view_Project.class);
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
            location.setText(loci);
            e_cost.setText(cost);

        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permission Needed")
                    .setMessage("Permission to storage is needed to allow you save this data")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new  String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE},
                                    STORAGE_PERMISSION_CODE);
                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
                    .create().show();
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new  String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(MainActivity.this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.refresh_activity){
            recreate();
        }

        else if(item.getItemId() == R.id.logout){

            Intent intent =new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper1 myDB = new MyDatabaseHelper1(MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    void storeDataInArray(){

        String id_string = pj_id.getText().toString();
        Cursor cursor =myDB.readAllData(id_string);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.getCount() == 0) {
                Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    item_id.add(cursor.getString(0));
                    item_name.add(cursor.getString(1));
                    item_cost.add(cursor.getString(2));
                    item_qtt.add(cursor.getString(3));
                    item_desc.add(cursor.getString(4));
                    item_date.add(cursor.getString(5));
                    item_supplier.add(cursor.getString(6));
                }
            }
        }

    }


}