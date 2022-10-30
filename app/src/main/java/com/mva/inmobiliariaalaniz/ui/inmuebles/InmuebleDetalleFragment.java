package com.mva.inmobiliariaalaniz.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mva.inmobiliariaalaniz.R;
import com.mva.inmobiliariaalaniz.adapter.InmuebleAdapter;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;

import java.util.List;

public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel vmDetalle;
    private TextView tvCodigoDetalle, tvDireccionDetalle, tvUsoDetalle, tvTipoDetalle, tvAmbientesDetalle, tvPrecioDetalle;
    private CheckBox checkBox;
    private ImageView ivAvatarDetalle;

    public static InmuebleDetalleFragment newInstance() {
        return new InmuebleDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);
        View view = inflater.inflate(R.layout.fragment_inmueble_detalle, container, false);

        vmDetalle.getMutableInmuebles().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                tvCodigoDetalle.setText(inmueble.getId() + "");
                tvAmbientesDetalle.setText(inmueble.getCantAmbientes() + "");
                tvDireccionDetalle.setText(inmueble.getDireccion());
                tvPrecioDetalle.setText(inmueble.getPrecio() + "");
                tvTipoDetalle.setText(inmueble.getTipo());
                tvUsoDetalle.setText(inmueble.getUso());
                checkBox.setChecked(inmueble.isEstado());
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        vmDetalle.disponible(checkBox.isChecked());
                    }
                });
                Glide.with(view.getContext())
                        .load(inmueble.getImagen())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivAvatarDetalle);
            }
        });
        inicializarVista(view);
        return view;

    }
    public void inicializarVista(View view){
        tvCodigoDetalle = view.findViewById(R.id.tvCodigoDetalle);
        tvAmbientesDetalle = view.findViewById(R.id.tvAmbientesDetalle);
        tvDireccionDetalle = view.findViewById(R.id.tvDireccionDetalle);
        tvPrecioDetalle = view.findViewById(R.id.tvPrecioDetalle);
        tvTipoDetalle = view.findViewById(R.id.tvTipoDetalle);
        tvUsoDetalle = view.findViewById(R.id.tvUsoDetalle);
        checkBox = view.findViewById(R.id.checkBox);
        ivAvatarDetalle = view.findViewById(R.id.ivAvatarDetalle);
        Bundle bundle = getArguments();
        vmDetalle.obtenerInmueble(getArguments());
    }



}