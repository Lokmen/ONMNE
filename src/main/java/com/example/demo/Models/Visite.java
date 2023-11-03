package com.example.demo.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="visite")

public class Visite {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long Id;
       private Date dateVisite;
        @ManyToOne
        @JoinColumn(name = "maladie_viste")
        private Maladie maladieID;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient_visite;
    @ManyToOne
    @JoinColumn(name="medecin_id")
    private Medecin medecin_visite;

    public Visite(Long id, Date dateVisite, Maladie maladieID, Patient patient_visite, Medecin medecin_visite) {
        Id = id;
        this.dateVisite = dateVisite;
        this.maladieID = maladieID;
        this.patient_visite = patient_visite;
        this.medecin_visite = medecin_visite;
    }

    public Patient getPatient_visite() {
        return patient_visite;
    }

    public void setPatient_visite(Patient patient_visite) {
        this.patient_visite = patient_visite;
    }

    public Medecin getMedecin_visite() {
        return medecin_visite;
    }

    public void setMedecin_visite(Medecin medecin_visite) {
        this.medecin_visite = medecin_visite;
    }

    public Visite() {

    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }



    public Maladie getMaladieID() {
        return maladieID;
    }

    public void setMaladieID(Maladie maladieID) {
        this.maladieID = maladieID;
    }
}
