package com.mva.inmobiliariaalaniz.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.mva.inmobiliariaalaniz.modelo.Contrato;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.modelo.Inquilino;
import com.mva.inmobiliariaalaniz.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoDetalleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inquilino> mutableInquilino;

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

        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String token = sp.getString("token", "-1");
        Call<Contrato> obtenerInquilinoPromesa = ApiRetrofit.getServiceInmobiliaria().obtenerContratos(token,inm.getId());

        obtenerInquilinoPromesa.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {

                if (response.isSuccessful()){
                    Inquilino inquilinoActual= response.body().getInquilino();
                    mutableInquilino.setValue(inquilinoActual);
                }
                else{
                    Toast.makeText(context, "Sin contratos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {

                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
