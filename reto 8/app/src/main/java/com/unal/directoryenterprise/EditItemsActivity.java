package com.unal.directoryenterprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class EditItemsActivity extends AppCompatActivity {
    private Button registerBtn;
    private TextInputEditText name;
    private TextInputEditText url;
    private TextInputEditText phone;
    private TextInputEditText email;
    private TextInputEditText products;
    private AutoCompleteTextView clasification;
    public static Integer POSITION;
    ArrayAdapter<String> arrayAdapter;
    String[] strings={"Consultoría","Desarrollo a la medida y/o fábrica de software"};
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items);
        registerBtn=findViewById(R.id.registerButton);
        name=findViewById(R.id.companyEditText);
        url=findViewById(R.id.urlEditText);
        phone=findViewById(R.id.phoneEditText);
        email=findViewById(R.id.mailEditText);
        products=findViewById(R.id.productAndServicesEditText);
        clasification=findViewById(R.id.clasificationEditText);
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.list_item,strings);
        clasification.setAdapter(arrayAdapter);

        db=new DBHelper(this);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (db.updateCompany(EditItemsActivity.POSITION+1,name.getText().toString(),
                        url.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        clasification.getText().toString(),
                        products.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Se Actualizó exitosamente el registro", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Hubo un problema al actualizar el registro", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.registerMenuItem:
                Intent intent=new Intent(getApplicationContext(),DirectoryRegisters.class);
                startActivity(intent);
                break;
        }
        return  true;
    }
}