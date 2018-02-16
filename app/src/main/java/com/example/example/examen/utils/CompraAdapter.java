package com.example.example.examen.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.example.examen.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by user on 2/15/18.
 */

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.ViewHolder> {

    public CompraAdapter(ArrayList<Compra> compras) {
        this.compras = compras;
    }

    ArrayList<Compra> compras;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_compra, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Compra compra = compras.get(position);
        holder.textViewnme.setText(compra.namecliente);
        holder.textViewmedicina.setText(compra.namemedicina);
        holder.textViewcantidad.setText(compra.qty);
    }

    @Override
    public int getItemCount() {
        return compras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewnme, textViewmedicina, textViewcantidad;
        public ViewHolder(View itemView) {
            super(itemView);

            this.textViewcantidad = itemView.findViewById(R.id.text_view_cantidad);
            this.textViewmedicina = itemView.findViewById(R.id.text_view_medicina);
            this.textViewnme = itemView.findViewById(R.id.text_view_name);

        }
    }
}
