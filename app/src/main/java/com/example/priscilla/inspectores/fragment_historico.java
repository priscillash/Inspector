package com.example.priscilla.inspectores;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class fragment_historico extends Fragment {

    private ListView mListView;

    //Adapter que se utilizará para completar la lista de históricos
    private CustomConsultaAdapter customConsultaAdapter;

    public fragment_historico() {
    //Se requiere constructor público por defecto
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        customConsultaAdapter = new CustomConsultaAdapter(getActivity(),ConsultaInfraccion.unalistaConsultas);

        // Seteo del adapter
        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(customConsultaAdapter);

        return view;
    }


    public class CustomConsultaAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<Consulta> mUnalistaConsultas;
        String idTicket;
        String matricula;
        String dateConsulta;


        public CustomConsultaAdapter(Context context, ArrayList<Consulta> unalistaConsultas){
            super();
            this.mContext = context;
            this.mUnalistaConsultas = unalistaConsultas;

        }


        @Override
        public int getCount() {
            return mUnalistaConsultas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_item_consulta,null);

            Consulta unaConsulta = mUnalistaConsultas.get(position);
            idTicket = unaConsulta.idTicket.toString();
            matricula = unaConsulta.matricula;
            dateConsulta = unaConsulta.dateConsulta;

            TextView textViewa = (TextView) convertView.findViewById(R.id.FechaHora);
            TextView textViewb = (TextView) convertView.findViewById(R.id.matricula);
            TextView textViewc = (TextView) convertView.findViewById(R.id.tckid);

            textViewa.setText(dateConsulta);
            textViewa.setTextSize(26);
            textViewa.setTextColor(getResources().getColor(R.color.black));
            textViewb.setText(matricula);
            textViewb.setTextSize(22);
            textViewc.setText(idTicket);
            textViewc.setTextSize(22);

            return convertView;
        }
    }


}
