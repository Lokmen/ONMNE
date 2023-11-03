package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class Patient extends UserModel {
    private Date datevisite;
    private String syndrome;

    @ManyToMany
    @JoinTable(
            name = "patient_atcd",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name ="atcd_id" ))
    Set<ATCD> PatientATCD;

    @OneToMany(mappedBy = "patient_visite" ,cascade = CascadeType.ALL)
    private Set<Visite> visites;

    public Patient(Long id, String username, String email, String password, String photo, Date datenes, Date datevisite, String syndrome, Set<ATCD> patientATCD, Set<Visite> visites) {
        super(id, username, email, password, photo, datenes);
        this.datevisite = datevisite;
        this.syndrome = syndrome;
        PatientATCD = patientATCD;
        this.visites = visites;
    }
    @JsonIgnore
    public Set<Visite> getVisites() {
        return visites;
    }

    public void setVisites(Set<Visite> visites) {
        this.visites = visites;
    }
    @JsonIgnore
    public Set<ATCD> getPatientATCD() {
        return PatientATCD;
    }

    public void setPatientATCD(Set<ATCD> patientATCD) {
        PatientATCD = patientATCD;
    }




    public Patient() {
    }



    public Date getDatevisite() {
        return datevisite;
    }


    public void setDatevisite(Date datevisite) {
        this.datevisite = datevisite;
    }

    public String getSyndrome() {
        return syndrome;
    }

    public void setSyndrome(String syndrome) {
        this.syndrome = syndrome;
    }


}
