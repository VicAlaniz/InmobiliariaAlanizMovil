package com.mva.inmobiliariaalaniz.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mva.inmobiliariaalaniz.modelo.Contrato;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;

import com.mva.inmobiliariaalaniz.request.ApiClient;

import java.io.Closeable;

public class ContratosDetallesViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Contrato> mutableContrato;
    private ApiClient api= ApiClient.getApi();

    public ContratosDetallesViewModel(@NonNull Application app) {
        super(app);
        this.context = app.getApplicationContext();
    }
    public LiveData<Contrato> getMutableContrato() {
        if(mutableContrato==null){
            mutableContrato=new MutableLiveData<>();
        }
        return mutableContrato;
    }

    public void obtenerContrato(Bundle bundle){
        Inmueble inm = (Inmueble) bundle.getSerializable("inmueble");
        Contrato con = api.obtenerContratoVigente(inm);
        mutableContrato.setValue(con);
    }

}