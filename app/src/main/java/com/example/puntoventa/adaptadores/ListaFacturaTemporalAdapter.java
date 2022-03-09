package com.example.puntoventa.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.puntoventa.Factura.FacturaTemporal;
import com.example.puntoventa.Productos.VerProducto;
import com.example.puntoventa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaFacturaTemporalAdapter extends RecyclerView.Adapter<ListaFacturaTemporalAdapter.FacturaViewHolder>{
    ArrayList<FacturaTemporal> listaFactura;
    ArrayList<FacturaTemporal> listaOriginal;

    public ListaFacturaTemporalAdapter(ArrayList<FacturaTemporal> listaFactura) {
        this.listaFactura = listaFactura;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaFactura);
    }
    @NonNull
    @Override
    public ListaFacturaTemporalAdapter.FacturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_factura, null, false);
        return new ListaFacturaTemporalAdapter.FacturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFacturaTemporalAdapter.FacturaViewHolder holder, int position) {
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
                List<FacturaTemporal> collecion = listaFactura.stream()
                        .filter(i -> i.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaFactura.clear();
                listaFactura.addAll(collecion);
            } else {
                for (FacturaTemporal c : listaOriginal) {
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
