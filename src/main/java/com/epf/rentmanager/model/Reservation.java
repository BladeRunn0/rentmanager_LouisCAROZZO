package com.epf.rentmanager.model;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private long client_id;

    private Client client;
    private long vehicle_id;
    private Vehicle vehicle;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(int id, long client_id, long vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(long client_id, long vehicle_id, LocalDate debut, LocalDate fin) {
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(int id, Client client, Vehicle vehicle, LocalDate debut, LocalDate fin){
        this.id = id;
        this.client = client;
        this.client_id = client.getId();
        this.vehicle = vehicle;
        this.vehicle_id = vehicle.getId();
        this.debut = debut;
        this.fin = fin;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicle_id=" + vehicle_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }

    public Client getClient() {
        return client;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setVehicle_id(long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Date toSqlDateDebut(){
        Date date = Date.valueOf(this.debut);
        return date;
    }

    public Date toSqlDateFin(){
        Date date = Date.valueOf(this.fin);
        return date;
    }
}
