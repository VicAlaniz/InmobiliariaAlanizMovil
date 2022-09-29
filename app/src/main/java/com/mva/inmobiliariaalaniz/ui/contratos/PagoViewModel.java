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
import com.mva.inmobiliariaalaniz.modelo.Pago;
import com.mva.inmobiliariaalaniz.request.ApiClient;

import java.util.List;

public class PagoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> mutablePago;
    private ApiClient api= ApiClient.getApi();

    public PagoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Pago>> getMutablePago() {
        if(mutablePago==null){
            mutablePago=new MutableLiveData<>();
        }
        return mutablePago;
    }

    public void obtenerPagos(Bundle bundle){
        Contrato contrato =(Contrato) bundle.getSerializable("contrato");
        List<Pago> pago= api.obtenerPagos(contrato);
        mutablePago.setValue(pago);
    }
}