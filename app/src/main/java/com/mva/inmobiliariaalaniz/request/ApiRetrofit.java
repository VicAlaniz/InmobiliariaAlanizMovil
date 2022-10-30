package com.mva.inmobiliariaalaniz.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mva.inmobiliariaalaniz.modelo.Contrato;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.modelo.Propietario;
import com.mva.inmobiliariaalaniz.modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiRetrofit {
    private static final String PATH="http://192.168.2.53:5000/api/";

    private static  ServiceInmobiliaria servicioInmobiliaria;

    public static ServiceInmobiliaria getServiceInmobiliaria(){

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        servicioInmobiliaria = retrofit.create(ServiceInmobiliaria.class);

        return servicioInmobiliaria;
    }

    public interface ServiceInmobiliaria {
        @POST("Propietarios/Login")
        Call<String> login(@Body Usuario usuario);

        @GET("Propietarios")
        Call<Propietario> obtenerPerfil(@Header("Authorization")String token);

        @PUT("Propietarios")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        @FormUrlEncoded
        @POST("Propietarios/reestablecer")
        Call<Propietario> reestablecerClave (@Field("email") String email);

        @GET("Inmuebles")
        Call<List<Inmueble>> ListarInmuebles(@Header("Authorization") String token);

        @POST("Inmuebles")
        Call<Inmueble> agregarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);


        @PUT("Inmuebles")
        Call<Inmueble> CambiarEstado(@Header("Authorization") String token, @Body Inmueble inmueble);

        @GET("Inquilinos/{id}")
        Call<Contrato> obtenerContratos (@Header("Authorization") String token, @Path("id") int id);

        @GET("Contratos")
        Call<List<Contrato>> contratosEnCurso (@Header("Authorization") String token);

    }
    public static int obtenerPropietarioActual(Context context){
        SharedPreferences sp= context.getSharedPreferences("propietarioActual",0);
        int id=sp.getInt("id",0);
        return id;
    }


}
