package com.epf.rentmanager.model;

public class Vehicle {
    private long id;
    private String constructeur;
    private byte nb_places;

    public Vehicle(int id, String constructeur, byte nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.nb_places = nb_places;
    }

    public Vehicle(long id, String constructeur, byte nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.nb_places = nb_places;
    }

    public Vehicle(String constructeur, byte nb_places) {
        this.constructeur = constructeur;
        this.nb_places = nb_places;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }



    public byte getNb_places() {
        return nb_places;
    }

    public void setNb_places(byte nb_places) {
        this.nb_places = nb_places;
    }
}
