package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="Maladie")

public class Maladie {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long Id;
    private String label;

@OneToMany(mappedBy = "maladieID",cascade = CascadeType.ALL)
private Collection<Visite> visiteCollection;


    public Maladie(Long id, String label, Collection<Visite> visiteCollection) {
        Id = id;
        this.label = label;
        this.visiteCollection = visiteCollection;
    }

    public Maladie() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    @JsonIgnore
    public Collection<Visite> getVisiteCollection() {
        return visiteCollection;
    }

    public void setVisiteCollection(Collection<Visite> visiteCollection) {
        this.visiteCollection = visiteCollection;
    }
}
