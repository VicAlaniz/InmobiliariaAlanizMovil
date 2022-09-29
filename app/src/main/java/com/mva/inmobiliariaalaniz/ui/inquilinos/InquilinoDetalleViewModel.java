package com.mva.inmobiliariaalaniz.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.modelo.Inquilino;
import com.mva.inmobiliariaalaniz.request.ApiClient;

public class InquilinoDetalleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inquilino> mutableInquilino;
    private ApiClient api= ApiClient.getApi();

    public InquilinoDetalleViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }
    public LiveData<Inquilino> getMutableInquilinos() {
        if(mutableInquilino==null){
            mutableInquilino=new MutableLiveData<>();
        }
        return mutableInquilino;
    }

    public void obtenerInquilino(Bundle bundle){
        Inmueble inm = (Inmueble) bundle.getSerializable("inmueble");
        Inquilino inq = api.obtenerInquilino(inm);
        mutableInquilino.setValue(inq);
    }

}
