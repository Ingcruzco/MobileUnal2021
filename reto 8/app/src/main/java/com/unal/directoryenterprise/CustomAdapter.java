package com.unal.directoryenterprise;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    ArrayList<String[]> array_list;
    String[] name;
    String[] url;
    String[] phone;
    String[] email;
    String[] products;
    String[] clasification;
    String[] ids;
    CustomAdapter(ArrayList<String[]> array_list){
        this.array_list=array_list;
        name=new String[array_list.size()];
        url=new String[array_list.size()];
        phone=new String[array_list.size()];
        email=new String[array_list.size()];
        products=new String[array_list.size()];
        clasification=new String[array_list.size()];
        ids=new String[array_list.size()];
        Integer counter=0;
        for (String[] item:array_list){
            ids[counter]=item[0];
            name[counter]=item[1];
            url[counter]=item[2];
            phone[counter]=item[3];
            email[counter]=item[4];
            products[counter]=item[5];
            clasification[counter]=item[6];
            counter+=1;
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(this.name[position]);
        holder.itemUrl.setText(this.url[position]);
        holder.itemEmail.setText(this.email[position]);
        holder.itemPhone.setText(this.phone[position]);
        holder.itemProduct.setText(this.products[position]);
        holder.itemClasification.setText(this.clasification[position]);
        holder.ID=Integer.parseInt(this.ids[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView itemName;
        private TextView itemUrl;
        private TextView itemPhone;
        private TextView itemEmail;
        private TextView itemProduct;
        private TextView itemClasification;
        private Button editBtn,deleteBtn;
        private Integer ID;
        DBHelper db;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.itemName);
            itemUrl=itemView.findViewById(R.id.itemUrl);
            itemPhone=itemView.findViewById(R.id.itemPhone);
            itemEmail=itemView.findViewById(R.id.itemEmail);
            itemProduct=itemView.findViewById(R.id.itemProducts);
            itemClasification=itemView.findViewById(R.id.itemClasification);
            editBtn=itemView.findViewById(R.id.Editbutton);
            deleteBtn=itemView.findViewById(R.id.deleteButton);
            editBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Editbutton:
                    Intent intent=new Intent(v.getContext(),EditItemsActivity.class);
                    EditItemsActivity.POSITION=getLayoutPosition();
                    //intent.putExtra("position",getLayoutPosition());
                    v.getContext().startActivity(intent);
                    break;
                case R.id.deleteButton:
                    Toast.makeText(v.getContext(),"Voy a eliminar el registro"+ID,Toast.LENGTH_LONG).show();
                    DBHelper db=new DBHelper(v.getContext());
                    db.deleteCompany(ID);
                    break;
            }

        }
    }
}
