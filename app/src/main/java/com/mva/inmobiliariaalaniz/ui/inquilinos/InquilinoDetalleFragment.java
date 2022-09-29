package com.mva.inmobiliariaalaniz.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mva.inmobiliariaalaniz.R;

import com.mva.inmobiliariaalaniz.modelo.Inquilino;
;

public class InquilinoDetalleFragment extends Fragment {
    private InquilinoDetalleViewModel vmDetalle;
    private TextView tvCodDetalle, tvNombreDetalle, tvApellidoDetalle, tvDniDetalle, tvMailDetalle, tvTelefonoDetalle, tvGaranteDetalle, tvTelGaranteDetalle;


    public static InquilinoDetalleFragment newInstance() {
        return new InquilinoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(InquilinoDetalleViewModel.class);
        View view = inflater.inflate(R.layout.fragment_inquilino_detalle, container, false);

        vmDetalle.getMutableInquilinos().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                tvCodDetalle.setText(inquilino.getIdInquilino() + "");
                tvNombreDetalle.setText(inquilino.getNombre());
                tvApellidoDetalle.setText(inquilino.getApellido());
                tvDniDetalle.setText(inquilino.getDNI() + "");
                tvMailDetalle.setText(inquilino.getEmail());
                tvTelefonoDetalle.setText(inquilino.getTelefono());
                tvGaranteDetalle.setText(inquilino.getNombreGarante());
                tvTelGaranteDetalle.setText(inquilino.getTelefonoGarante());

            }
        });
        inicializarVista(view);
        return view;

    }
    public void inicializarVista(View view){
        tvCodDetalle = view.findViewById(R.id.tvCodDetalle);
        tvNombreDetalle = view.findViewById(R.id.tvNombreDetalle);
        tvApellidoDetalle = view.findViewById(R.id.tvApellidoDetalle);
        tvDniDetalle = view.findViewById(R.id.tvDniDetalle);
        tvMailDetalle = view.findViewById(R.id.tvMailDetalle);
        tvTelefonoDetalle = view.findViewById(R.id.tvTelefonoDetalle);
        tvGaranteDetalle = view.findViewById(R.id.tvGaranteDetalle);
        tvTelGaranteDetalle = view.findViewById(R.id.tvTelGaranteDetalle);

        vmDetalle.obtenerInquilino(getArguments());
    }


}
