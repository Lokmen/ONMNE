package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.Set;

@Entity
public class Medecin extends UserModel{
    private String cnom;
    private String specialite;
    private String hopital;
    @OneToMany(mappedBy = "medecin_visite" ,cascade = CascadeType.ALL)
    private Set<Visite> visites;

    public Medecin(Long id, String username, String email, String password, String photo, Date datenes, String cnom, String specialite, String hopital, Set<Visite> visites) {
        super(id, username, email, password, photo, datenes);
        this.cnom = cnom;
        this.specialite = specialite;
        this.hopital = hopital;
        this.visites = visites;
    }

    public Medecin() {
    }
@JsonIgnore
    public Set<Visite> getVisites() {
        return visites;
    }

    public void setVisites(Set<Visite> visites) {
        this.visites = visites;
    }

    public String getCnom() {
        return cnom;
    }

    public void setCnom(String cnom) {
        this.cnom = cnom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getHopital() {
        return hopital;
    }

    public void setHopital(String hopital) {
        this.hopital = hopital;
    }
}
