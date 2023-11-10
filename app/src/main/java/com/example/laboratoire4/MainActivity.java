package com.example.laboratoire4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editNomClient;
    private EditText editNumTel;
    private Button btnImporterDonnees;
    private boolean formatting = false; // Éviter une boucle infinie
    private boolean backspacing = false; // Détecter si le backspace est en cours
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Récupérer les éléments de l'interface utilisateur
        editNomClient = findViewById(R.id.editNomClient);
        editNumTel = findViewById(R.id.editNumTel);
        editNumTel.addTextChangedListener(phoneNumberTextWatcher);
        btnImporterDonnees = findViewById(R.id.btnImporterDonnees);

        // Ajouter un écouteur de clic au bouton "Choix dans le menu"
        btnImporterDonnees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le nom du client et le numéro de téléphone saisis
                String nomClient = editNomClient.getText().toString();
                String numTel = editNumTel.getText().toString();
                    if(numTel.length()==12){
                        if(!nomClient.isEmpty()){
                        // Créer une intention pour ouvrir l'activité de commande
                        Intent intent = new Intent(MainActivity.this, activity_commande.class);
                        intent.putExtra("nomClient", nomClient);
                        intent.putExtra("numTel", numTel);
                        // Démarrer l'activité de commande
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Veuillez saisir un nom complet", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Veuillez saisir un numero de telephone complet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String formatPhoneNumber(String phoneNumber) {
        StringBuilder formattedNumber = new StringBuilder();
        int compteur = 0;
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (Character.isDigit(c)) {
                formattedNumber.append(c);
                compteur++;
                // Ajouter un tiret après chaque groupe de 3 chiffres, sauf le dernier groupe
                if (compteur % 3 == 0 && compteur < 9) {
                    formattedNumber.append('-');
                }
                if (compteur > 9) {
                    break;
                }
            }
        }

        return formattedNumber.toString();
    }

    // TextWatcher pour surveiller les changements de texte
    private TextWatcher phoneNumberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (count > after) {
                backspacing = true;
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (formatting) return; // Éviter la boucle infinie
            if (!backspacing) {
                formatting = true;
                String input = s.toString();
                String formattedNumber = formatPhoneNumber(input);
                if (!formattedNumber.equals(input)) {
                    editNumTel.setText(formattedNumber);
                    editNumTel.setSelection(formattedNumber.length());
                }
                formatting = false;
            } else {
                // Le backspace est en cours, ne pas reformater
                backspacing = false;
            }
        }
    };
}
