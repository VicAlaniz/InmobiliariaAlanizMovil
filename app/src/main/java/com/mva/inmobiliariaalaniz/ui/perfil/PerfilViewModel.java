package com.mva.inmobiliariaalaniz.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mva.inmobiliariaalaniz.modelo.Propietario;
import com.mva.inmobiliariaalaniz.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> cambio;
    private MutableLiveData<String> texto;

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
        Propietario propietario;
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String token = sp.getString("token", "-1");
        Call<Propietario> tokenP = ApiRetrofit.getServiceInmobiliaria().obtenerPerfil(token);
        tokenP.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()) {
                    Propietario propietario = response.body();
                    mPropietario.postValue(propietario);
                } else {
                    Log.d("salida", "Propietario sin datos");
                }

            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(context,"Error de conexión",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actualizarPropietario(String boton, Propietario p){

        if(boton.equals("ACTUALIZAR")){

            SharedPreferences sp = context.getSharedPreferences("token", 0);
            String token = sp.getString("token", "-1");
            Call<Propietario> tokenP = ApiRetrofit.getServiceInmobiliaria().actualizarPropietario(token, p);
            tokenP.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()) {
                        Propietario propietario = response.body();
                        Toast.makeText(context,"Propietario actualizado correctamente",Toast.LENGTH_SHORT).show();
                        mPropietario.postValue(propietario);
                    } else {
                        Log.d("salida", "Propietario sin datos");
                    }

                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(context,"Error de conexión",Toast.LENGTH_SHORT).show();
                }
            });
            cambio.setValue(false);
            texto.setValue("EDITAR");
        } else {
            cambio.setValue(true);
            texto.setValue("ACTUALIZAR");
        }
    }
}