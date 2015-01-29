package com.example.priscilla.inspectores;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.app.FragmentTransaction;

import android.view.ViewGroup;
import android.widget.Button;


// ESTARIA BUENO QUE FUERA UN DIALOG FRAGMENT
public class fragment_logout extends Fragment{

    private Button button_cerrarSesion;
    private Button button_cancelar;
    private CharSequence mTitle;
    public fragment_logout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Button button_cerrarSesion = (Button) getView().findViewById(R.id.button2);
        Button button_cancelar = (Button) getView().findViewById(R.id.button3);

        button_cancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

                System.out.println(mTitle);

            }

        });

        button_cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIntent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(openIntent);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_logout, container, false);

        return view;
    }


}
