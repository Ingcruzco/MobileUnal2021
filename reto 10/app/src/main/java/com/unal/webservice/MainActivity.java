package com.unal.webservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ListElement> elements;
    Button btn;
    JsonParser mData;
    Spinner spinner;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        elements=new ArrayList<>();
        btn=findViewById(R.id.button);
        spinner=findViewById(R.id.spinner);
        editText=findViewById(R.id.editTextTextPersonName);
        String[] placeTypeList={"municipio","sexo","area_tem_tica","nombre_del_curso"};
        String[] placeNameList={"municipio","sexo","area_tem_tica","nombre_del_curso"};
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,placeNameList));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url=null;
                String sUsername = editText.getText().toString();
                int i =spinner.getSelectedItemPosition();
                if (sUsername.matches("")){
                    url = "https://www.datos.gov.co/resource/nzyx-5vfw.json";
                }else{
                    url= "https://www.datos.gov.co/resource/nzyx-5vfw.json?" + placeTypeList[i] + "=" + editText.getText().toString();

                }
                new PlaceTask().execute(url);
                init();
            }
        });
    }
    public void init(){

        ListAdapter listAdapter= new ListAdapter(elements,this);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    private class PlaceTask extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... strings) {
            String data=null;
            try {
                data= downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }
    private String downloadUrl(String string) throws IOException {
        URL url= new URL(string);
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream=connection.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder=new StringBuilder();
        String line="";
        while ((line=reader.readLine())!=null){
            builder.append(line);
        }
        String data=builder.toString();
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String, String>> mapList = null;
            JSONArray object = null;
            elements.clear();
            try {
                object = new JSONArray(strings[0]);
                for (int i=0; i<object.length();i++){
                    String text=object.get(i).toString();
                    transformData(text);
                }

                //mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }
    }
    public String[] transformData(String s){
        s=s.replace("{","");
        s=s.replace("}","");
        String[] tempString=s.split(",") ;
        for(int i=0;i<tempString.length;i++){
            tempString[i]=tempString[i].split(":")[1];
            tempString[i]=tempString[i].replace("\"","");
        }

        elements.add(new ListElement( tempString[0],tempString[1],tempString[4],tempString[6],tempString[9]
                ,tempString[7],tempString[8]));



        return tempString;
    }
}