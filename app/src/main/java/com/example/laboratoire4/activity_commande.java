package com.example.laboratoire4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class activity_commande extends AppCompatActivity {

    private TextView textNomClient;
    private TextView textNumTelClient;
    private Button btnCommander;
    private ArrayList<Repas> repasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        // Récupérer les éléments de l'interface utilisateur
        textNomClient = findViewById(R.id.editNomClient);
        textNumTelClient = findViewById(R.id.editNumTel);
        btnCommander = findViewById(R.id.btnCommander);
        btnCommander = findViewById(R.id.btnCommander); // Bouton de commande
        // Récupérer les données du client depuis l'activité précédente
        String nomClient = getIntent().getStringExtra("nomClient");
        String numTelClient = getIntent().getStringExtra("numTel");

        // Mettre à jour les TextViews avec les données du client
        Log.v("nomClient","nomClient: "+nomClient);
        Log.v("numTelClient","numTelClient: "+numTelClient);
        textNomClient.setText(nomClient);
        textNumTelClient.setText(numTelClient);

// Chargez les données des repas en utilisant la méthode parseMenuFromXML
        repasList  = parseMenuFromXML();

        fragment_menu_repas menuRepasFragment = new fragment_menu_repas();
        DetailsRepasFragment fragmentDetails = new DetailsRepasFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, menuRepasFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.fragmentDetails, fragmentDetails).commit();

        //Envoie de la liste
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listRepas", repasList);
        menuRepasFragment.setArguments(bundle);

// Gérez le clic sur le bouton de commande
        btnCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajoutez ici la logique pour commander le repas sélectionné.
                // Vous pouvez récupérer le repas sélectionné en fonction de la vue qui a été cliquée.
                // Assurez-vous d'implémenter la logique pour suivre le repas sélectionné.
            }
        });
    }
//    @Override
//    public void onItemClick(Repas repasChoisi) { //, String prixRepas, String descRepas
//        Log.d("repasList.toString()", "Liste de repas:"+repasList.toString());
//        // Créez un fragment de détails et transmettez-lui le nom du repas
//        DetailsRepasFragment fragment = DetailsRepasFragment.newInstance(repasChoisi);
//        //, prixRepas, descRepas
//
//        // Remplacez le fragment actuel par le fragment de détails
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragmentContainer, fragment)
//                .addToBackStack(null)
//                .commit();
//    }
    private ArrayList<Repas> parseMenuFromXML() {
        ArrayList<Repas> repasList = new ArrayList<>();
        XmlResourceParser xrp = getResources().getXml(R.xml.menu);

        try {
            int eventType = xrp.getEventType();
            Repas repas = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = xrp.getName();

                    if ("repas".equals(tagName)) {
                        repas = new Repas();
                    } else if ("numero".equals(tagName)) {
                        repas.setNoRepas(Integer.parseInt(xrp.nextText()));
                    } else if ("nom".equals(tagName)) {
                        repas.setNom(xrp.nextText());
                    } else if ("description".equals(tagName)) {
                        repas.setDescription(xrp.nextText());
                    } else if ("categorie".equals(tagName)) {
                        repas.setCategorie(xrp.nextText());
                    } else if ("prix".equals(tagName)) {
                        repas.setPrix(Double.parseDouble(xrp.nextText()));
                    }
                } else if (eventType == XmlPullParser.END_TAG && "repas".equals(xrp.getName())) {
                    if (repas != null) {
                        repasList.add(repas);
                    }
                }
                eventType = xrp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return repasList;
    }
}
