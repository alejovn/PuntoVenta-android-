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

import com.example.puntoventa.Factura.FacturaMostrar;
import com.example.puntoventa.Productos.VerProducto;
import com.example.puntoventa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaFacturaAdapter extends RecyclerView.Adapter<ListaFacturaAdapter.FacturaViewHolder>{
    ArrayList<FacturaMostrar> listaFactura;
    ArrayList<FacturaMostrar> listaOriginal;

    public ListaFacturaAdapter(ArrayList<FacturaMostrar> listaFactura) {

        this.listaFactura = listaFactura;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaFactura);
    }

    @NonNull
    @Override
    public ListaFacturaAdapter.FacturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_factura, null, false);
        return new ListaFacturaAdapter.FacturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFacturaAdapter.FacturaViewHolder holder, int position) {
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFE0E0"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        holder.CantidadF.setText(String. valueOf(listaFactura.get(position).getCantidad()));
        holder.DescripcionF.setText(listaFactura.get(position).getDescripcion());
        double total=0;
        total=(listaFactura.get(position).getCantidad()*listaFactura.get(position).getUnidad());
        holder.TotalF.setText(String. valueOf(total));
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaFactura.clear();
            listaFactura.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<FacturaMostrar> collecion = listaFactura.stream()
                        .filter(i -> i.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaFactura.clear();
                listaFactura.addAll(collecion);
            } else {
                for (FacturaMostrar c : listaOriginal) {
                    if (c.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaFactura.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaFactura.size();
    }

    public class FacturaViewHolder extends RecyclerView.ViewHolder {
        TextView CantidadF, DescripcionF, TotalF;
        public FacturaViewHolder(@NonNull View itemView) {
            super(itemView);
            CantidadF = itemView.findViewById(R.id.CantidadF);
            DescripcionF = itemView.findViewById(R.id.DescripcionF);
            TotalF = itemView.findViewById(R.id.TotalF);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerProducto.class);
                    intent.putExtra("ID", listaFactura.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
