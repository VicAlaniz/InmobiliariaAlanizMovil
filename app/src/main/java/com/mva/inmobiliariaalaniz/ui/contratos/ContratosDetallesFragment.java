package com.mva.inmobiliariaalaniz.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.mva.inmobiliariaalaniz.R;

import com.mva.inmobiliariaalaniz.modelo.Contrato;



public class ContratosDetallesFragment extends Fragment {

    private ContratosDetallesViewModel vmDetalle;
    private TextView tvCodigo, tvFechaIContrato, tvFechaFContrato, tvMontoAlquiler, tvInqContrato, tvInmContrato;



    public static ContratosDetallesFragment newInstance() {
        return new ContratosDetallesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(ContratosDetallesViewModel.class);
        View view = inflater.inflate(R.layout.fragment_contratos, container, false);

        vmDetalle.getMutableContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                tvCodigo.setText(contrato.getId()+"");
                tvFechaIContrato.setText(contrato.getFechaInicio());
                tvMontoAlquiler.setText(contrato.getMontoAlquiler()+"");
                tvFechaFContrato.setText(contrato.getFechaFin());
                tvInmContrato.setText("Dirección: "+ contrato.getInmueble().getDireccion());
                tvInqContrato.setText(contrato.getInquilino().getNombre()+" "+contrato.getInquilino().getApellido());

            }
        });
        inicializarVista(view);
        return view;

    }
    public void inicializarVista(View view){
        tvCodigo= view.findViewById(R.id.tvCodigo);
        tvFechaIContrato= view.findViewById(R.id.tvFechaIContrato);
        tvMontoAlquiler= view.findViewById(R.id.tvMontoAlquiler);
        tvFechaFContrato= view.findViewById(R.id.tvFechaFContrato);
        tvInmContrato= view.findViewById(R.id.tvInmContrato);
        tvInqContrato= view.findViewById(R.id.tvInqContrato);

        Bundle bundle = getArguments();

        vmDetalle.obtenerContrato(bundle);
    }
}