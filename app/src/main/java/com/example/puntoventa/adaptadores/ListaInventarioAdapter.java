package com.example.puntoventa.adaptadores;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.puntoventa.Inicio;
import com.example.puntoventa.Productos.ProductoMostrar;
import com.example.puntoventa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaInventarioAdapter extends RecyclerView.Adapter<ListaInventarioAdapter.InventarioViewHolder>{
    ArrayList<ProductoMostrar> listaInventario;
    ArrayList<ProductoMostrar> listaOriginal;
    private final ArrayList<Integer> selected = new ArrayList<>();

    public ListaInventarioAdapter(ArrayList<ProductoMostrar> listaInventario) {

        this.listaInventario = listaInventario;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaInventario);
    }

    @NonNull
    @Override
    public ListaInventarioAdapter.InventarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_inventario, null, false);
        return new ListaInventarioAdapter.InventarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaInventarioAdapter.InventarioViewHolder holder, int position) {
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFE0E0"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        holder.viewNombreI.setText(listaInventario.get(position).getNombre());
        holder.viewCodigoI.setText(listaInventario.get(position).getCodigo());
        holder.viewCantidadI.setText(String. valueOf(listaInventario.get(position).getCantidad()));
    }
    public void filtrado(final String txtBuscar, boolean bandera) {
        int longitud = txtBuscar.length();
        if(bandera==false){
            if (longitud == 0) {
                listaInventario.clear();
                listaInventario.addAll(listaOriginal);
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    List<ProductoMostrar> collecion = listaInventario.stream()
                            .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                            .collect(Collectors.toList());
                    listaInventario.clear();
                    listaInventario.addAll(collecion);
                } else {
                    for (ProductoMostrar c : listaOriginal) {
                        if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                            listaInventario.add(c);
                        }
                    }
                }
            }
        }else{
            if (longitud == 0) {
                listaInventario.clear();
                listaInventario.addAll(listaOriginal);
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    List<ProductoMostrar> collecion = listaInventario.stream()
                            .filter(i -> i.getCodigo().toLowerCase().contains(txtBuscar.toLowerCase()))
                            .collect(Collectors.toList());
                    listaInventario.clear();
                    listaInventario.addAll(collecion);
                } else {
                    for (ProductoMostrar c : listaOriginal) {
                        if (c.getCodigo().toLowerCase().contains(txtBuscar.toLowerCase())) {
                            listaInventario.add(c);
                        }
                    }
                }
            }
            Inicio inicio = new Inicio();
            inicio.bandera=false;
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listaInventario.size();
    }

    public class InventarioViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombreI, viewCodigoI, viewCantidadI;
        public InventarioViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombreI = itemView.findViewById(R.id.viewNombreI);
            viewCodigoI = itemView.findViewById(R.id.viewCodigoI);
            viewCantidadI = itemView.findViewById(R.id.viewCantidadI);
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
                    Inicio inicio = new Inicio();
                    inicio.Cargar(listaInventario.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
