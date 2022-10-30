package com.mva.inmobiliariaalaniz.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> mutableInmuebles;

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
        List<Inmueble> listaInmuebles;
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String token = sp.getString("token", "-1");
        Call<List<Inmueble>> tokenI = ApiRetrofit.getServiceInmobiliaria().ListarInmuebles(token);
        tokenI.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()) {
                    List<Inmueble> listaInmuebles = response.body();
                    mutableInmuebles.postValue(listaInmuebles);
                } else {
                    Log.d("salida", "Inmueble sin datos");
                }

            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(context,"Error de conexión",Toast.LENGTH_SHORT).show();
            }
        });
    }

}