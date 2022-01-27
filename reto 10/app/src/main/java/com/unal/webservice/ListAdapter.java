package com.unal.webservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mInflater=LayoutInflater.from(context);
        this.mData = itemList;
        this.context=context;

    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView codMunicipio, municipio, sexo, fechaInicio, areaTematica, duracion, nombreCurso;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codMunicipio=itemView.findViewById(R.id.valorCodigoMunicipio);
            municipio=itemView.findViewById(R.id.valorMunicipio);
            sexo=itemView.findViewById(R.id.valorSexo);
            fechaInicio=itemView.findViewById(R.id.valorFechaInicio);
            areaTematica=itemView.findViewById(R.id.valorAreaTematica);
            duracion=itemView.findViewById(R.id.valorDuracion);
            nombreCurso=itemView.findViewById(R.id.valorNombreCurso);
        }

        public void bindData(final ListElement item){
            codMunicipio.setText(item.getCodigoMunicipio());
            municipio.setText(item.getMunicipio());
            sexo.setText(item.getSexo());
            fechaInicio.setText(item.getFechaInicio());
            areaTematica.setText(item.getAreaTematica());
            duracion.setText(item.getDuracion());
            nombreCurso.setText(item.getNombreCurso());
        }
    }

    public void setItems(List<ListElement> items){mData=items;}

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.card_view,null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
