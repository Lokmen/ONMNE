package com.example.demo.Models;

import jakarta.persistence.*;

@Entity
@Table(name="Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer Id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private  ERole name;

    public Role() {
    }

    public Role(Integer id, ERole name) {
        Id = id;
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
