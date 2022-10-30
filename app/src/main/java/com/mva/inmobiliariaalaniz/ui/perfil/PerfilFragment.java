package com.mva.inmobiliariaalaniz.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mva.inmobiliariaalaniz.R;
import com.mva.inmobiliariaalaniz.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel vmPerfil;
    private EditText etCodigo,etNombre,etApellido,etDni,etEmail,etPass, etTel;
    private Button btEditar;
    private ImageView ivAvatar;
    private String idAvatar = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vmPerfil = new ViewModelProvider(this).get(PerfilViewModel.class);
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        vmPerfil.getMutablePropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                etCodigo.setText(propietario.getId()+"");
                etNombre.setText(propietario.getNombre());
                etApellido.setText(propietario.getApellido());
                etDni.setText(propietario.getDni()+"");
                etEmail.setText(propietario.getEmail());
                etTel.setText(propietario.getTelefono());
                etPass.setText(propietario.getClave());
                Glide.with(getContext())
                        .load(propietario.getImgPerfil())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivAvatar);

                idAvatar = propietario.getImgPerfil();

            }
        });
        vmPerfil.getMutableCambio().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean valor) {

                etNombre.setEnabled(valor);
                etApellido.setEnabled(valor);
                etDni.setEnabled(valor);
                etTel.setEnabled(valor);

            }
        });
        vmPerfil.getMutableTexto().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String texto) {
                btEditar.setText(texto);
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        etCodigo=view.findViewById(R.id.etCodigo);
        etNombre = view.findViewById(R.id.etNombre);
        etApellido= view.findViewById(R.id.etApellido);
        etDni= view.findViewById(R.id.etDni);
        etEmail=view.findViewById(R.id.etEmail);
        etTel= view.findViewById(R.id.etTel);
        etPass= view.findViewById(R.id.etPass);
        btEditar= view.findViewById(R.id.btEditar);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        vmPerfil.ObtenerUsuario();
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = btEditar.getText().toString();
                int id = Integer.parseInt(etCodigo.getText().toString());
                String nombre = etNombre.getText().toString();
                String apellido = etApellido.getText().toString();
                String dni = etDni.getText().toString();
                String tel = etTel.getText().toString();
                String mail = etEmail.getText().toString();
                String pass = etPass.getText().toString();


                Propietario p = new Propietario(id, dni, nombre, apellido, mail, pass, tel, idAvatar);
                vmPerfil.actualizarPropietario(texto, p);

            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}