package com.epf.rentmanager.model;

import java.sql.Date;
import java.time.LocalDate;

public class Client {

    private long id;
    private String nom;
    private String prenom;
    private String mail;
    private LocalDate naissance;

    public Client(long id, String nom, String prenom, String mail, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.naissance = naissance;
    }

    public Client(Client client) {
        this.id = client.getId();
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
        this.mail = client.getMail();
        this.naissance = client.getNaissance();
    }

    public Client(String nom, String prenom, String mail, LocalDate naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.naissance = naissance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", naissance=" + naissance +
                '}';
    }

    public int getAge(){
        int yearDiff = LocalDate.now().getYear() - this.naissance.getYear();
        return yearDiff;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public Date toSqlDateNaissance(){
        Date date = Date.valueOf(this.naissance);
        return date;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }
}
