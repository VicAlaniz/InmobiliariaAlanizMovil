package com.mva.inmobiliariaalaniz.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mva.inmobiliariaalaniz.R;
import com.mva.inmobiliariaalaniz.modelo.Pago;
import com.mva.inmobiliariaalaniz.adapter.PagoAdapter;

import java.util.List;

public class PagoFragment extends Fragment {

    private PagoViewModel mViewModel;
    private ListView lvPago;
    private List<Pago> listaPago;

    public static PagoFragment newInstance() {
        return new PagoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PagoViewModel.class);
        View view = inflater.inflate(R.layout.fragment_pago, container, false);

        mViewModel.getMutablePago().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago>pago) {
                listaPago= pago;
                PagoAdapter pag = new PagoAdapter(getContext(),R.layout.pago_item,listaPago);
                lvPago.setAdapter(pag);
            }
        });
        inicializarVista(view);
        return view;
    }
    private void inicializarVista(View view){
        lvPago =view.findViewById(R.id.lvPago);
        Bundle bundle = getArguments();
        mViewModel.obtenerPagos(bundle);
    }
}