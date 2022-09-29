package com.mva.inmobiliariaalaniz.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mva.inmobiliariaalaniz.R;
import com.mva.inmobiliariaalaniz.adapter.InmuebleAdapter;
import com.mva.inmobiliariaalaniz.databinding.FragmentInmueblesBinding;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {

    private InmueblesViewModel vm;
    private RecyclerView rvInmuebles;
    private List<Inmueble> listaInmuebles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(InmueblesViewModel.class);
        View view = inflater.inflate(R.layout.fragment_inmuebles, container, false);

        vm.getMutableInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                listaInmuebles= inmuebles;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                rvInmuebles.setLayoutManager(gridLayoutManager);
                InmuebleAdapter par = new InmuebleAdapter(listaInmuebles,getContext(), getLayoutInflater());
                rvInmuebles.setAdapter(par);
            }
        });
        inicializarVista(view);
        return view;
    }
    private void inicializarVista(View view){
        rvInmuebles =view.findViewById(R.id.rvInmueble);
        vm.obtenerInmuebles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}