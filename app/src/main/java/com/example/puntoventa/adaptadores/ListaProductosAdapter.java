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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.puntoventa.Productos.ProductoMostrar;
import com.example.puntoventa.Productos.VerProducto;
import com.example.puntoventa.R;
import com.example.puntoventa.Usuarios.VerUsuario;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaProductosAdapter extends RecyclerView.Adapter<ListaProductosAdapter.ProductoViewHolder> {
    ArrayList<ProductoMostrar> listaProductos;
    ArrayList<ProductoMostrar> listaOriginal;
    private final ArrayList<Integer> selected = new ArrayList<>();

    public ListaProductosAdapter(ArrayList<ProductoMostrar> listaProductos) {
        this.listaProductos = listaProductos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaProductos);
    }

    @NonNull
    @Override
    public ListaProductosAdapter.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_producto, null, false);
        return new ListaProductosAdapter.ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProductosAdapter.ProductoViewHolder holder, int position) {
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFE0E0"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        holder.viewNombrePP.setText(listaProductos.get(position).getNombre());
        holder.viewCodigoPP.setText(listaProductos.get(position).getCodigo());
        holder.viewCantidadPP.setText(String. valueOf(listaProductos.get(position).getCantidad()));
        holder.viewStockMinimoPP.setText(String. valueOf(listaProductos.get(position).getStockMi()));
        holder.viewPrecioVPP.setText(String. valueOf(listaProductos.get(position).getPrecioV()));
        holder.viewPrecioCPP.setText(String. valueOf(listaProductos.get(position).getPrecioC()));
        holder.viewDescripcionPP.setText(listaProductos.get(position).getDescripcion());
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaProductos.clear();
            listaProductos.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ProductoMostrar> collecion = listaProductos.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaProductos.clear();
                listaProductos.addAll(collecion);
            } else {
                for (ProductoMostrar c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaProductos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombrePP, viewCodigoPP, viewPrecioVPP, viewPrecioCPP, viewDescripcionPP, viewCantidadPP, viewStockMinimoPP;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombrePP = itemView.findViewById(R.id.viewNombrePP);
            viewCodigoPP = itemView.findViewById(R.id.viewCodigoPP);
            viewCantidadPP = itemView.findViewById(R.id.viewCantidadPP);
            viewPrecioCPP = itemView.findViewById(R.id.viewPrecioCPP);
            viewPrecioVPP = itemView.findViewById(R.id.viewPrecioVPP);
            viewDescripcionPP = itemView.findViewById(R.id.viewDescripcionPP);
            viewStockMinimoPP = itemView.findViewById(R.id.viewStockMinimoPP);
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
                    Intent intent = new Intent(context, VerProducto.class);
                    intent.putExtra("ID", listaProductos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
