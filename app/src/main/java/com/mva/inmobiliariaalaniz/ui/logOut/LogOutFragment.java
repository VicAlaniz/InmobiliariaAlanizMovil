package com.mva.inmobiliariaalaniz.ui.logOut;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.mva.inmobiliariaalaniz.R;
import com.mva.inmobiliariaalaniz.databinding.FragmentLogoutBinding;
import com.mva.inmobiliariaalaniz.login.LoginActivity;

import java.util.NavigableMap;


public class LogOutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        muestraDialog(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void muestraDialog(View view){

        new AlertDialog.Builder(view.getContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Seguro desea cerrar?")
                .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                       //Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        //startActivity(intent);
                        System.exit(0);
                    }
                })

                .setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        //No hace nada
                    }
                }).show();


    }
}