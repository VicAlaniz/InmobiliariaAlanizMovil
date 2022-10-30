package com.mva.inmobiliariaalaniz.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mva.inmobiliariaalaniz.MainActivity;
import com.mva.inmobiliariaalaniz.modelo.Propietario;
import com.mva.inmobiliariaalaniz.modelo.Usuario;
import com.mva.inmobiliariaalaniz.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> error_visibility;
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Integer> getErrorVisibility() {
        if (error_visibility == null) { error_visibility = new MutableLiveData<>(); }
        return error_visibility;
    }
    public void login(String email, String pass) {
        Usuario usuario = new Usuario(email, pass);
        Call<String> tokenPromesa = ApiRetrofit.getServiceInmobiliaria().login(usuario);
        tokenPromesa.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    error_visibility.setValue(View.INVISIBLE);
                    SharedPreferences sp = context.getSharedPreferences("token", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", "Bearer " + response.body());
                    editor.commit();
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else {
                    error_visibility.setValue(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
        public void reestablecerClave(String email){
            Call<Propietario> tokenR = ApiRetrofit.getServiceInmobiliaria().reestablecerClave(email);
            tokenR.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Correo enviado", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context, "Error al enviar correo", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(context, "Error de conexión"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

}
