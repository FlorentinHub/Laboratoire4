package com.example.laboratoire4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class fragment_menu_repas extends Fragment {
    View vue;
    ArrayList listRepas;
    ListView lv_menuRepas;
    Repas repasChoisi;
    public fragment_menu_repas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vue = inflater.inflate(R.layout.fragment_menu_repas, container, false);
        lv_menuRepas=vue.findViewById(R.id.lv_menuRepas);

        listRepas = getArguments().getParcelableArrayList("listRepas");
        //Ajout de la liste de Repas au fragment
        ArrayAdapter<Repas> adapter = new ArrayAdapter<Repas>(getContext(), android.R.layout.simple_list_item_1, listRepas);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        lv_menuRepas.setAdapter(adapter);

        lv_menuRepas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                repasChoisi = (Repas) parent.getItemAtPosition(position);
                ((activity_commande) requireActivity()).setRepasSelectionne(repasChoisi);
                //Envoi de la liste
                Bundle bundle = new Bundle();
                bundle.putParcelable("repasChoisi", repasChoisi);
                getParentFragmentManager().setFragmentResult("envoiRepas", bundle);
            }
        });
        return vue;
    }
}