package com.mva.inmobiliariaalaniz.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.request.ApiClient;

import java.util.List;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inmueble> mutableInmueble;
    private ApiClient api= ApiClient.getApi();

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }
    public LiveData<Inmueble> getMutableInmuebles() {
        if(mutableInmueble==null){
            mutableInmueble=new MutableLiveData<>();
        }
        return mutableInmueble;
    }

    public void obtenerInmueble(Bundle bundle){
        mutableInmueble.setValue((Inmueble) bundle.getSerializable("inmueble"));
    }
    public void disponible(Boolean b){
        Inmueble inmueble= mutableInmueble.getValue();
        inmueble.setEstado(b);
        api.actualizarInmueble(inmueble);

    }

}