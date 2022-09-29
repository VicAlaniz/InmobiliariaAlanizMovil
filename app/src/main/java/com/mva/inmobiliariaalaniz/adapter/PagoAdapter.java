package com.mva.inmobiliariaalaniz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mva.inmobiliariaalaniz.R;
import com.mva.inmobiliariaalaniz.modelo.Pago;

import java.util.List;

public class PagoAdapter extends ArrayAdapter<Pago> {

private List<Pago> lista;
private Context contexto;
private LayoutInflater layoutInflater;

public PagoAdapter(@NonNull Context context, int resource, @NonNull List<Pago> objects) {
        super(context, resource, objects);
        lista = objects;
        contexto = context;
        layoutInflater =(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        View item = view;
        if(item == null){

        item = layoutInflater.inflate(R.layout.pago_item,parent,false);

        }
        Pago pago = lista.get(position);
        TextView tvCodigoP = item.findViewById(R.id.tvCodP);
        TextView tvNumeroP = item.findViewById(R.id.tvNumeroP);
        TextView tvCodigoC = item.findViewById(R.id.tvCodC);
        TextView tvImporteP = item.findViewById(R.id.tvImpP);
        TextView tvFechaP =item.findViewById(R.id.tvFechaP);
        tvCodigoP.setText(pago.getIdPago()+"");
        tvNumeroP.setText(pago.getNumero()+"");
        tvCodigoC.setText(pago.getContrato().getIdContrato()+"");
        tvImporteP.setText(pago.getImporte()+"");
        tvFechaP.setText(pago.getFechaDePago());
        return  item;
        }
}
