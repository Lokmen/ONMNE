package com.example.demo.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
    @Table(name="ATCD")

    public class ATCD {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;
        private String label;
    @ManyToMany(mappedBy = "PatientATCD")
    Set<Patient> patients;




    public ATCD(Long id, String label) {
            Id = id;
            this.label = label;
        }

        public ATCD() {
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
    }