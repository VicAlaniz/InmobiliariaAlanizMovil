package com.mva.inmobiliariaalaniz.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mva.inmobiliariaalaniz.modelo.Propietario;
import com.mva.inmobiliariaalaniz.request.ApiClient;

import java.util.Locale;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> cambio;
    private MutableLiveData<String> texto;
    private ApiClient api = ApiClient.getApi();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();

    }

    public LiveData<Propietario> getMutablePropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }
    public LiveData<Boolean> getMutableCambio() {
        if (cambio == null) {
            cambio = new MutableLiveData<>();
        }
        return cambio;
    }

    public LiveData<String> getMutableTexto() {
        if (texto == null) {
            texto = new MutableLiveData<>();
        }
        return texto;
    }

    public void ObtenerUsuario() {
        Propietario p = api.obtenerUsuarioActual();
        mPropietario.setValue(p);
    }

    public void actualizarPropietario(String boton, Propietario p){

        if(boton.equals("ACTUALIZAR")){
            api.actualizarPerfil(p);
            cambio.setValue(false);
            texto.setValue("EDITAR");
        } else {
            cambio.setValue(true);
            texto.setValue("ACTUALIZAR");
        }
    }
}