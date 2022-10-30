package com.mva.inmobiliariaalaniz.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;
import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.modelo.Ubicacion;
import com.mva.inmobiliariaalaniz.request.ApiRetrofit;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Bitmap> mutableFoto;
    private MutableLiveData<Ubicacion> ubicacionLiveData;
    private Ubicacion ubicacion;

    public AgregarInmuebleViewModel(@NonNull Application application)
    {
        super(application);
        this.context = application.getApplicationContext();
    }
    public LiveData<Bitmap> getMutableFoto() {
        if(mutableFoto == null) {
            mutableFoto = new MutableLiveData<>();
        }
        return mutableFoto;
    }
    public MutableLiveData<Ubicacion> getUbicacionActual(){
        if(ubicacionLiveData==null){
            ubicacionLiveData=new MutableLiveData<>();
        }
        return ubicacionLiveData;
    }


    public void obtenerUbicacion() {

        ubicacion = new Ubicacion();
        SharedPreferences sp= context.getSharedPreferences("ubicacion",-1);
        Float ubicacionX=sp.getFloat("coordenadax",-1);
        Float ubicacionY=sp.getFloat("coordenaday",-1);
        ubicacion.setLatitud(ubicacionX);
        ubicacion.setLongitud(ubicacionY);
        ubicacionLiveData.setValue(ubicacion);

    }

    public void AgregarInmueble(Inmueble inmueble) {
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String token = sp.getString("token", "-1");
        Call<Inmueble> tokenI = ApiRetrofit.getServiceInmobiliaria().agregarInmueble(token, inmueble);
        tokenI.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Agregado correctamente", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Error al agregar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {

            }
        });
    }
        public void sacarFoto(int requestCode, int resultCode, Intent data, int REQUEST_IMAGE_CAPTURE){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagen = (Bitmap) extras.get("data");

            ByteArrayOutputStream baos= new ByteArrayOutputStream();

            imagen.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] b= baos.toByteArray();
            mutableFoto.postValue(imagen);
        }
    }

}