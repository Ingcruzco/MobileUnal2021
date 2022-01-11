package com.unal.directoryenterprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;

import java.util.ArrayList;

public class DirectoryRegisters extends AppCompatActivity {
    DBHelper db;
    Button companyBtn,filterBtn;
    EditText editTextName,editTextClasification;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_registers);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);

        db=new DBHelper(this);
        ArrayList<String[]> array_list = db.getAllCompanies();
        RecyclerView.Adapter adapter=new CustomAdapter(array_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        cardView=(CardView)findViewById(R.id.card_view);
        editTextName=findViewById(R.id.editTextName);
        editTextClasification=findViewById(R.id.editTextClasification);
        filterBtn=findViewById(R.id.filterButton);
        companyBtn=findViewById(R.id.floating_action_button);
        companyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DirectoryInput.class);
                startActivity(intent);
            }
        });
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String[]> array_list = db.getData(editTextName.getText().toString(),editTextClasification.getText().toString());
                RecyclerView.Adapter adapter=new CustomAdapter(array_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                recyclerView.setAdapter(adapter);
            }
        });


    }

}