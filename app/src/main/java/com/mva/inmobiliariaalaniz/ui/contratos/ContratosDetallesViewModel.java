package com.mva.inmobiliariaalaniz.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mva.inmobiliariaalaniz.modelo.Contrato;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;

import com.mva.inmobiliariaalaniz.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosDetallesViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Contrato> mutableContrato;


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
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String token = sp.getString("token", "-1");
        Call<Contrato> tokenC = ApiRetrofit.getServiceInmobiliaria().obtenerContratos(token,inm.getId());

        tokenC.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {

                if (response.isSuccessful()){
                    Contrato contratoActual= response.body();
                    mutableContrato.setValue(contratoActual);
                }
                else{
                    Toast.makeText(context, "Error al buscar contrato", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}