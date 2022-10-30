package com.mva.inmobiliariaalaniz.ui.inmuebles;

import static android.Manifest.permission_group.CAMERA;


import static androidx.core.content.PermissionChecker.checkSelfPermission;

import androidx.core.content.PermissionChecker;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.mva.inmobiliariaalaniz.R;
import com.mva.inmobiliariaalaniz.modelo.Inmueble;
import com.mva.inmobiliariaalaniz.modelo.Ubicacion;
import com.mva.inmobiliariaalaniz.request.ApiRetrofit;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class AgregarInmuebleFragment extends Fragment {

    private AgregarInmuebleViewModel AgregarViewModel;
    private EditText etDirAgInm,etCantAmbientesAgInm,etPrecioAgInm, etCoordenadaE, etCoordenadaN;
    private Spinner spTipoAgInm,spUsoAgInm;
    private CheckBox cbEstadoAgInm;
    private ImageView ivInmuebleAgInm;
    private String encoded;
    private static int REQUEST_IMAGE_CAPTURE=1;
    private Button btGuardarInm;
    private Inmueble inm = new Inmueble();

    public static AgregarInmuebleFragment newInstance() {
        return new AgregarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        AgregarViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);

        View view = inflater.inflate(R.layout.fragment_agregar_inmueble, container, false);
        AgregarViewModel.getUbicacionActual();
        AgregarViewModel.obtenerUbicacion();
        AgregarViewModel.getMutableFoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {

                ivInmuebleAgInm.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
                byte [] b = baos.toByteArray();

                encoded = Base64.getEncoder().encodeToString(b);

            }
        });
        AgregarViewModel.getUbicacionActual().observe(getViewLifecycleOwner(), new Observer<Ubicacion>() {
            @Override
            public void onChanged(Ubicacion ubicacion) {

                inm.setCoordenadaE(Double.parseDouble(ubicacion.getLatitud().toString()));
                inm.setCoordenadaN(Double.parseDouble(ubicacion.getLongitud().toString()));
                etCoordenadaE.setText(inm.getCoordenadaE()+" ");
                etCoordenadaN.setText(inm.getCoordenadaN()+" ");
            }
        });
        solicitarPermisos();
        Inicializar(view);
        return view;
    }

    private void Inicializar(View view)
    {
        //AgregarViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(AgregarInmuebleViewModel.class);

        etDirAgInm = view.findViewById(R.id.etDirAgInm);
        etCantAmbientesAgInm = view.findViewById(R.id.etCantAmbientesAgInm);
        etPrecioAgInm = view.findViewById(R.id.etPrecioAgInm);
        spUsoAgInm = view.findViewById(R.id.spUsoAgInm);
        spTipoAgInm = view.findViewById(R.id.spTipoAgInm);
        cbEstadoAgInm = view.findViewById(R.id.cbEstadoAgInm);
        etCoordenadaE = view.findViewById(R.id.etCoordenadaE);
        etCoordenadaN = view.findViewById(R.id.etCoordenadaN);
        ivInmuebleAgInm = view.findViewById(R.id.ivInmuebleAgInm);
        btGuardarInm = view.findViewById(R.id.btGuardarInm);

        String[] opUso = {"Hogar","Comercio"};
        String[] opTipo = {"Casa","Depto","Comercio","Otro"};
        CargarSpinner(spUsoAgInm,opUso);
        CargarSpinner(spTipoAgInm,opTipo);

        ivInmuebleAgInm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicializarCamara(view);
            }
        });


        btGuardarInm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmueble inmueble = new Inmueble();
                inmueble.setDireccion(etDirAgInm.getText().toString());
                inmueble.setUso(spUsoAgInm.getSelectedItem().toString());
                inmueble.setTipo(spTipoAgInm.getSelectedItem().toString());
                inmueble.setCantAmbientes(Integer.parseInt(etCantAmbientesAgInm.getText().toString()));
                inmueble.setPrecio(Double.parseDouble(etPrecioAgInm.getText().toString()));
                inmueble.setImagen(encoded);
                inmueble.setCoordenadaE(Double.valueOf(etCoordenadaE.getText().toString()));
                inmueble.setCoordenadaN(Double.valueOf(etCoordenadaN.getText().toString()));
                int idPropietario = ApiRetrofit.obtenerPropietarioActual(getContext());
                inmueble.setPropietarioId(idPropietario);

                if (cbEstadoAgInm.isChecked()) {
                    inmueble.setEstado(true);
                }

                AgregarViewModel.AgregarInmueble(inmueble);
            }
        });
    }

    public void CargarSpinner(Spinner spinner, String[] opciones)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,opciones);
        spinner.setAdapter(adapter);
    }
    public void inicializarCamara(View view){
        Intent tomarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (tomarFoto.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(tomarFoto, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        AgregarInmuebleFragment.super.onActivityResult(requestCode, resultCode, data);
        AgregarViewModel.sacarFoto(requestCode, resultCode, data, REQUEST_IMAGE_CAPTURE);
    }

    public void solicitarPermisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(getContext(),CAMERA)
                != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, 1000);
        }
    }


}