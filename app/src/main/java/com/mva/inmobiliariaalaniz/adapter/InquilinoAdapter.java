package com.mva.inmobiliariaalaniz.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mva.inmobiliariaalaniz.R;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;

import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder>{

    private List<Inmueble> listaInmuebles;
    private Context contexto;
    private LayoutInflater li;



    public InquilinoAdapter(List<Inmueble> listaInm, Context context, LayoutInflater layoutInflater) {

        this.listaInmuebles = listaInm;
        this.contexto = context;
        this.li = layoutInflater;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= li.inflate(R.layout.inquilino_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Inmueble inmueble = listaInmuebles.get(position);
        holder.tvDireccion.setText(inmueble.getDireccion());

        //holder.ivInmueble.setImageResource(inmueble.getImagen());
        Glide.with(contexto)
                .load(listaInmuebles.get(position).getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivInmueble);
        holder.btVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmueble);
                Navigation.findNavController(v).navigate(R.id.nav_inquilino_detalle, bundle);
            }
        });
        holder.btVerContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmueble);
                Navigation.findNavController(v).navigate(R.id.nav_contratos, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion;
        private ImageView ivInmueble;
        private Button btVer;
        private Button btVerContrato;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            ivInmueble = itemView.findViewById(R.id.ivImagen);
            btVer = itemView.findViewById(R.id.btVer);
            btVerContrato = itemView.findViewById(R.id.btVerContrato);
        }
    }

}
