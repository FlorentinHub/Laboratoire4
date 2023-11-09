package com.example.laboratoire4;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Repas implements Parcelable {
    private int NoRepas;
    private String Nom;
    private String Description;
    private String Categorie;
    private double Prix;

    public Repas(int NoRepas, String Nom, String Description, String Categorie, double Prix) {
        this.NoRepas = NoRepas;
        this.Nom = Nom;
        this.Description = Description;
        this.Categorie = Categorie;
        this.Prix = Prix;
    }
    public Repas() {
    }

    protected Repas(Parcel in) {
        NoRepas = in.readInt();
        Nom = in.readString();
        Description = in.readString();
        Categorie = in.readString();
        Prix = in.readDouble();
    }

    public static final Creator<Repas> CREATOR = new Creator<Repas>() {
        @Override
        public Repas createFromParcel(Parcel in) {
            return new Repas(in);
        }

        @Override
        public Repas[] newArray(int size) {
            return new Repas[size];
        }
    };

    public int getNoRepas() {
        return NoRepas;
    }

    public void setNoRepas(int noRepas) {
        NoRepas = noRepas;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double prix) {
        Prix = prix;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(NoRepas);
        dest.writeString(Nom);
        dest.writeString(Description);
        dest.writeString(Categorie);
        dest.writeDouble(Prix);
    }
    @Override
    public String toString(){


        return this.getNom()+"   -   "+this.getPrix();
    }
}