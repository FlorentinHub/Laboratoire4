package com.example.laboratoire4;

import android.Manifest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import android.telephony.SmsManager;
import android.widget.Toast;

public class activity_commande extends AppCompatActivity {

    private TextView textNomClient;
    private TextView textNumTelClient;
    private Button btnCommander;
    private ArrayList<Repas> repasList = new ArrayList<>();
    private Repas repasSelectionne;
    private Commande commande;
    private NotificationManager mng_notif;
    private static final String CHANNEL_ID = "MiamNotifCommande";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        mng_notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            createNotificationChannel();

        // Récupérer les éléments de l'interface utilisateur
        textNomClient = findViewById(R.id.editNomClient);
        textNumTelClient = findViewById(R.id.editNumTel);
        btnCommander = findViewById(R.id.btnCommander);

        // Récupérer les données du client depuis l'activité précédente
        String nomClient = getIntent().getStringExtra("nomClient");
        String numTelClient = getIntent().getStringExtra("numTel");

        // Mettre à jour les TextViews avec les données du client
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

        btnCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repasSelectionne!=null){
                commande = new Commande(
                        genererNumeroCommande(),
                        textNomClient.getText().toString(),
                        textNumTelClient.getText().toString(),
                        repasSelectionne.getNoRepas(),
                        repasSelectionne.getNom(),
                        repasSelectionne.getPrix()
                );
                if (commande != null) {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        envoyerSMS(commande);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    }
                    notifier();
                }
            }else {
                    Toast.makeText(activity_commande.this, "Veuillez choisir un repas pour commander", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void notifier() {
            int notificationID = commande.getNoCommande();
            String canalID = CHANNEL_ID;
            Notification nbuilder = new NotificationCompat.Builder(this, canalID)
                    .setSmallIcon(R.drawable.icon_repas)
                    .setContentTitle("Miam-O-Pizza - Labo4")
                    .setContentText("La commande #" + commande.getNoCommande() + " est prete ! Repas: " + commande.getNomRepas() + "Total: " + repasSelectionne.getPrix() + "$ +tx")
                    .setChannelId(canalID)
                    .build();
            mng_notif.notify(notificationID, nbuilder);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence nom = "MIAM-O-PIZZA";
            String description = "Description";
            int priorite = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel canal = new NotificationChannel(CHANNEL_ID, nom, priorite);

            canal.setDescription(description);
            canal.enableLights(true);
            canal.setLightColor(Color.RED);
            mng_notif.createNotificationChannel(canal);
        }
    }

    public void envoyerSMS(Commande commande){
        // Construire le message SMS
        String message = "Bonjour "+commande.getNomClient()+", Voici votre commande : " + commande.getNomRepas() + ", " + commande.getPrixRepas() + " $. Merci de commander chez nous!";
            // Utiliser l'API SmsManager pour envoyer le SMS
            SmsManager smsmgr = SmsManager.getDefault();
            smsmgr.sendTextMessage(commande.getTelClient(), null,message,null,null);
            // Confirmation sms
            Toast.makeText(activity_commande.this, "Commande passée avec succès !", Toast.LENGTH_SHORT).show();
            Log.i("SMS", "SMS envoyé : " + message);
    }
    public static int genererNumeroCommande() {
        // Générateur de nombres aléatoires
        Random random = new Random();
        int numeroCommande = random.nextInt(90000) + 10000;
        return numeroCommande;
    }
public void setRepasSelectionne(Repas repas) {
        this.repasSelectionne = repas;
    }
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
