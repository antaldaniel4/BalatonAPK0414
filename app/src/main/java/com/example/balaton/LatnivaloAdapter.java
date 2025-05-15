package com.example.balaton;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class LatnivaloAdapter extends RecyclerView.Adapter<LatnivaloAdapter.ViewHolder> {
    private List<Latnivalo> lista;

    public LatnivaloAdapter(List<Latnivalo> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.latnivalo_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Latnivalo l = lista.get(pos);
        holder.textNev.setText(l.getNev());
        holder.textVaros.setText("Város: " + l.getVaros());
        holder.textKategoria.setText("Kategóriák: " + String.join(", ", l.getKategoria()));
        holder.textLeiras.setText(l.getLeiras());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void frissit(List<Latnivalo> ujLista) {
        this.lista = ujLista;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNev, textVaros, textKategoria, textLeiras;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNev = itemView.findViewById(R.id.textNev);
            textVaros = itemView.findViewById(R.id.textVaros);
            textKategoria = itemView.findViewById(R.id.textKategoria);
            textLeiras = itemView.findViewById(R.id.textLeiras);
        }
    }
}