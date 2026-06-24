package com.example.waitwise;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int token;
    private String status;
    private boolean emergency;

    public Patient() {
    }

    public Patient(String name, int token, String status, boolean emergency) {
        this.name = name;
        this.token = token;
        this.status = status;
        this.emergency = emergency;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getToken() {
        return token;
    }

    public String getStatus() {
        return status;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}