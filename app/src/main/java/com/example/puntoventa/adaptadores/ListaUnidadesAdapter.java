package com.example.puntoventa.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.puntoventa.Productos.VerProducto;
import com.example.puntoventa.R;
import com.example.puntoventa.Unidades.UnidadesMostrar;
import com.example.puntoventa.Unidades.VerUnidad;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaUnidadesAdapter extends RecyclerView.Adapter<com.example.puntoventa.adaptadores.ListaUnidadesAdapter.UnidadViewHolder> {
    ArrayList<UnidadesMostrar> listaUnidades;
    ArrayList<UnidadesMostrar> listaOriginal;
    private final ArrayList<Integer> selected = new ArrayList<>();
        public ListaUnidadesAdapter(ArrayList<UnidadesMostrar> listaUnidades) {

            this.listaUnidades = listaUnidades;
            listaOriginal = new ArrayList<>();
            listaOriginal.addAll(listaUnidades);
            System.out.println("Aqui llego ListaProductosAdapter");
        }
        @NonNull
        @Override
        public com.example.puntoventa.adaptadores.ListaUnidadesAdapter.UnidadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_unidad, null, false);
            return new com.example.puntoventa.adaptadores.ListaUnidadesAdapter.UnidadViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull com.example.puntoventa.adaptadores.ListaUnidadesAdapter.UnidadViewHolder holder, int position) {
            if(position %2 == 1)
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
            }
            else
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFE0E0"));
            }
            holder.viewIdL.setText(String.valueOf(listaUnidades.get(position).getId()));
            holder.viewNombreL.setText(listaUnidades.get(position).getNombre());
            holder.viewNombre_cortoL.setText(listaUnidades.get(position).getNombre_corto());
        }
        public void filtrado(final String txtBuscar) {
            int longitud = txtBuscar.length();
            if (longitud == 0) {
                listaUnidades.clear();
                listaUnidades.addAll(listaOriginal);
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    List<UnidadesMostrar> collecion = listaUnidades.stream()
                            .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                            .collect(Collectors.toList());
                    listaUnidades.clear();
                    listaUnidades.addAll(collecion);
                } else {
                    for (UnidadesMostrar c : listaOriginal) {
                        if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                            listaUnidades.add(c);
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }
        @Override
        public int getItemCount() {
            return listaUnidades.size();
        }
        public class UnidadViewHolder extends RecyclerView.ViewHolder {
            TextView viewIdL, viewNombreL, viewNombre_cortoL;
            public UnidadViewHolder(@NonNull View itemView) {
                super(itemView);
                viewIdL = itemView.findViewById(R.id.IdL);
                viewNombreL = itemView.findViewById(R.id.NombreL);
                viewNombre_cortoL = itemView.findViewById(R.id.NombreCortoL);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setBackgroundColor(Color.parseColor("#06FFE4"));
                        if (selected.isEmpty()){
                            selected.add(getAdapterPosition());
                        }else {
                            int oldSelected = selected.get(0);
                            selected.clear();
                            selected.add(getAdapterPosition());
                            notifyItemChanged(oldSelected);
                        }
                        Context context = view.getContext();
                        Intent intent = new Intent(context, VerUnidad.class);
                        intent.putExtra("ID", listaUnidades.get(getAdapterPosition()).getId());
                        context.startActivity(intent);
                    }
                });
            }
        }
}
