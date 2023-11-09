package com.example.laboratoire4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

public class DetailsRepasFragment extends Fragment {
        TextView tv_menuItemName, tv_menuItemDescription, tv_menuItemPrice, tv_menuCategorieName;
        View vue;
    public DetailsRepasFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vue = inflater.inflate(R.layout.menu_repas_fragment, container, false);

        tv_menuItemName=vue.findViewById(R.id.tv_menuItemName);
        tv_menuCategorieName=vue.findViewById(R.id.tv_menuCategorieName);
        tv_menuItemDescription=vue.findViewById(R.id.tv_menuItemDescription);
        tv_menuItemPrice=vue.findViewById(R.id.tv_menuItemPrice);

        getParentFragmentManager().setFragmentResultListener("envoiRepas", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Repas repasChoisi = result.getParcelable("repasChoisi");
                Log.d("repas",repasChoisi.getNom().toString());
                Log.d("repas",tv_menuItemName.getText().toString());
                tv_menuItemName.setText(repasChoisi.getNom().toString());
                tv_menuCategorieName.setText(repasChoisi.getCategorie().toString());
                tv_menuItemDescription.setText(repasChoisi.getDescription().toString());
                tv_menuItemPrice.setText(String.valueOf(repasChoisi.getPrix()));
            }
        });
        return vue;
    }
}
