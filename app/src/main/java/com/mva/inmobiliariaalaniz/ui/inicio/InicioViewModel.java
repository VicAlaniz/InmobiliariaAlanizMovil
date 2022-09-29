package com.mva.inmobiliariaalaniz.ui.inicio;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.mva.inmobiliariaalaniz.modelo.Propietario;

public class InicioViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<LeerMapa> mutableLeerMapa;
    private LatLng ult;
    public InicioViewModel(Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }
    public LiveData<LeerMapa> getMutableLeerMapa() {
        if (mutableLeerMapa == null) {
            mutableLeerMapa = new MutableLiveData<>();
        }
        return mutableLeerMapa;
    }
    public void leerMapa(){
        mutableLeerMapa.setValue(new LeerMapa(context));
    }
}