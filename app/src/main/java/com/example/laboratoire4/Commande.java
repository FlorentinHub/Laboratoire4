package com.example.laboratoire4;

public class Commande {
    private int NoCommande;
    private String NomClient;
    private String TelClient;
    private int NoRepas;
    private String NomRepas;
    private double PrixRepas;

    public Commande(int NoCommande, String NomClient, String TelClient, int NoRepas, String NomRepas, double PrixRepas) {
        this.NoCommande = NoCommande;
        this.NomClient = NomClient;
        this.TelClient = TelClient;
        this.NoRepas = NoRepas;
        this.NomRepas = NomRepas;
        this.PrixRepas = PrixRepas;
    }
    public int getNoCommande() {
        return NoCommande;
    }

    public void setNoCommande(int noCommande) {
        NoCommande = noCommande;
    }

    public String getNomClient() {
        return NomClient;
    }

    public void setNomClient(String nomClient) {
        NomClient = nomClient;
    }

    public String getTelClient() {
        return TelClient;
    }

    public void setTelClient(String telClient) {
        TelClient = telClient;
    }

    public int getNoRepas() {
        return NoRepas;
    }

    public void setNoRepas(int noRepas) {
        NoRepas = noRepas;
    }

    public String getNomRepas() {
        return NomRepas;
    }

    public void setNomRepas(String nomRepas) {
        NomRepas = nomRepas;
    }

    public double getPrixRepas() {
        return PrixRepas;
    }

    public void setPrixRepas(double prixRepas) {
        PrixRepas = prixRepas;
    }

}
