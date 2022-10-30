package com.mva.inmobiliariaalaniz.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inmueble> mutableInmueble;
    private int id;


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
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        this.mutableInmueble.setValue(inmueble);
    }
    public void disponible(Boolean  b){
        Inmueble inmueble= mutableInmueble.getValue();
        inmueble.setEstado(b);

        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String token = sp.getString("token", "-1");
        Call<Inmueble> tokenI = ApiRetrofit.getServiceInmobiliaria().CambiarEstado(token, inmueble);
        tokenI.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()) {
                    response.body().toString();
                    Toast.makeText(context,"Estado modificado",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,"Error al modificar estado",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(context,"Error de conexión",Toast.LENGTH_SHORT).show();
            }
        });

    }

}