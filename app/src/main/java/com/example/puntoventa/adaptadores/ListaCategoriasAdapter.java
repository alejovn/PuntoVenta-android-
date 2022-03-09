package com.example.puntoventa.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.puntoventa.Categorias.CategoriasMostrar;
import com.example.puntoventa.Categorias.VerCategoria;
import com.example.puntoventa.Productos.VerProducto;
import com.example.puntoventa.R;
import com.example.puntoventa.Unidades.UnidadesMostrar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaCategoriasAdapter extends RecyclerView.Adapter<com.example.puntoventa.adaptadores.ListaCategoriasAdapter.CategoriaViewHolder> {
    ArrayList<CategoriasMostrar> listaCategorias;
    ArrayList<CategoriasMostrar> listaOriginal;
    private final ArrayList<Integer> selected = new ArrayList<>();
    public ListaCategoriasAdapter(ArrayList<CategoriasMostrar> listaCategorias) {

        this.listaCategorias = listaCategorias;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaCategorias);
        System.out.println("Aqui llego ListaProductosAdapter");
    }
    @NonNull
    @Override
    public com.example.puntoventa.adaptadores.ListaCategoriasAdapter.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_categoria, null, false);
        return new com.example.puntoventa.adaptadores.ListaCategoriasAdapter.CategoriaViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull com.example.puntoventa.adaptadores.ListaCategoriasAdapter.CategoriaViewHolder holder, int position) {
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFE0E0"));
        }
        holder.viewIdL.setText(String.valueOf(listaCategorias.get(position).getId()));
        holder.viewNombreL.setText(listaCategorias.get(position).getNombre());
    }
    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaCategorias.clear();
            listaCategorias.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<CategoriasMostrar> collecion = listaCategorias.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaCategorias.clear();
                listaCategorias.addAll(collecion);
            } else {
                for (CategoriasMostrar c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaCategorias.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }
    public class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView viewIdL, viewNombreL;
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            viewIdL = itemView.findViewById(R.id.IdL);
            viewNombreL = itemView.findViewById(R.id.NombreL);
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
                    Intent intent = new Intent(context, VerCategoria.class);
                    intent.putExtra("ID", listaCategorias.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
