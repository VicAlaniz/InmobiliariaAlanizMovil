package com.mva.inmobiliariaalaniz.ui.inmuebles;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.request.ApiClient;

import java.util.List;

public class InmueblesViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> mutableInmuebles;
    private ApiClient api= ApiClient.getApi();

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> getMutableInmuebles() {
        if(mutableInmuebles==null){
            mutableInmuebles=new MutableLiveData<>();
        }
        return mutableInmuebles;
    }

    public void obtenerInmuebles(){
        List<Inmueble> listaInmuebles = api.obtnerPropiedades();
        mutableInmuebles.setValue(listaInmuebles);
    }

}