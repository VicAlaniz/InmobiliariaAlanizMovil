package com.mva.inmobiliariaalaniz.ui.inquilinos;

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
import com.mva.inmobiliariaalaniz.adapter.InquilinoAdapter;
import com.mva.inmobiliariaalaniz.databinding.FragmentInquilinosBinding;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.modelo.Inquilino;
import com.mva.inmobiliariaalaniz.ui.inmuebles.InmueblesViewModel;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel vm;
    private RecyclerView rvInquilinos;
    private List<Inmueble> listaInmuebles;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);

        View view = inflater.inflate(R.layout.fragment_inquilinos, container, false);

        vm.getMutableInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                listaInmuebles= inmuebles;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rvInquilinos.setLayoutManager(gridLayoutManager);
                InquilinoAdapter par = new InquilinoAdapter(listaInmuebles,getContext(), getLayoutInflater());
                rvInquilinos.setAdapter(par);
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){

        rvInquilinos = view.findViewById(R.id.rvInquilinos);
        vm.listarInmuebles();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}