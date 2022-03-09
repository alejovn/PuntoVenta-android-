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


import com.example.puntoventa.R;
import com.example.puntoventa.Usuarios.UsuarioMostrar;
import com.example.puntoventa.Usuarios.VerUsuario;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.UsuarioViewHolder> {

    ArrayList<UsuarioMostrar> listaUsuarios;
    ArrayList<UsuarioMostrar> listaOriginal;
    private final ArrayList<Integer> selected = new ArrayList<>();
    public ListaUsuariosAdapter(ArrayList<UsuarioMostrar> listaContactos) {
        this.listaUsuarios = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_usuario, null, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFE0E0"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        holder.viewNombre.setText(listaUsuarios.get(position).getNombre());
        holder.viewApellidos.setText(listaUsuarios.get(position).getApellidos());
        holder.viewCorreo.setText(listaUsuarios.get(position).getCorreo());
        File file = new File(listaUsuarios.get(position).getImagen());
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        holder.imageView.setImageBitmap(myBitmap);

    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaUsuarios.clear();
            listaUsuarios.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<UsuarioMostrar> collecion = listaUsuarios.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaUsuarios.clear();
                listaUsuarios.addAll(collecion);
            } else {
                for (UsuarioMostrar c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaUsuarios.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewApellidos, viewCorreo;
        ImageView imageView;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewApellidos = itemView.findViewById(R.id.viewApellidos);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);
            imageView = itemView.findViewById(R.id.imageView);

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
                    Intent intent = new Intent(context, VerUsuario.class);
                    intent.putExtra("ID", listaUsuarios.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
