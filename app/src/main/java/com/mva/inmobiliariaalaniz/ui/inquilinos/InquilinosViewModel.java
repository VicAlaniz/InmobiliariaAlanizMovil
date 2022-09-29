package com.mva.inmobiliariaalaniz.ui.inquilinos;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.modelo.Inquilino;
import com.mva.inmobiliariaalaniz.request.ApiClient;

import java.util.List;

public class InquilinosViewModel extends AndroidViewModel {

    private Context context;

    private MutableLiveData<List<Inmueble>> mutableInmuebles;
    private ApiClient api= ApiClient.getApi();

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.api = ApiClient.getApi();
    }

    public LiveData<List<Inmueble>> getMutableInmuebles() {
        if(mutableInmuebles==null){
            mutableInmuebles=new MutableLiveData<>();
        }
        listarInmuebles();
        return mutableInmuebles;
    }

    public void listarInmuebles() {
        List<Inmueble> inmueble = api.obtenerPropiedadesAlquiladas();
        mutableInmuebles.setValue(inmueble);
    }


}