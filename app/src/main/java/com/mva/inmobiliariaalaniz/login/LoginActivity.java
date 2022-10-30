package com.mva.inmobiliariaalaniz.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mva.inmobiliariaalaniz.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsuario,etPassword;
    private Button btLogin, btCambiarClave;
    private LoginViewModel vmLogin;
    private TextView tvError;
    private SensorManager sensorManager;
    private LeerSensor leerSensor;
    private Context context;
    private Sensor acelera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_login);
        inicializarVista();
        vmLogin = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        vmLogin.getErrorVisibility().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                tvError.setVisibility(visibility);
            }
        });
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, 1000);
    }
        leerSensor = new LeerSensor(context);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelera = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(leerSensor, acelera, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(leerSensor, acelera, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void inicializarVista(){
        btLogin= findViewById(R.id.btLogin);
        etUsuario =findViewById(R.id.etUsuario);
        etPassword= findViewById(R.id.etPassword);
        tvError= findViewById(R.id.tvError);
        btCambiarClave = findViewById(R.id.btCambiarClave);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vmLogin.login(
                        etUsuario.getText().toString(),
                        etPassword.getText().toString()
                );
            }
        });
        etUsuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvError.setVisibility(View.INVISIBLE);
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvError.setVisibility(View.INVISIBLE);
            }
        });
        btCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsuario.getText().toString().equals("") || etUsuario.getText().toString()==null ){
                    Toast.makeText(context, "Debe ingresar un email", Toast.LENGTH_SHORT).show();
                }
                else{
                    vmLogin.reestablecerClave(etUsuario.getText().toString());
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(leerSensor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(leerSensor);
    }
    private class LeerSensor implements SensorEventListener {

        public LeerSensor(Context contexto) {
            context = contexto;
        }

        @Override
        public void onSensorChanged(SensorEvent evento) {
            if (evento.values[0] >= 8.421635 ) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:2664569233"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        }

        @Override
        public void onAccuracyChanged (Sensor sensor,int i){

        }
    }



}