package com.example.waitwise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    @Autowired
    private PatientRepository repo;

    private int currentToken = 0;
    private int avgTime = 10;

    public Patient addPatient(String name, boolean emergency) {
        int token = repo.findAll().size() + 1;
        Patient patient = new Patient(name, token, "WAITING", emergency);
        return repo.save(patient);
    }

    public Patient callNext() {
        List<Patient> patients = repo.findAll();

        for (Patient p : patients) {
            if (p.getStatus().equals("IN_PROGRESS")) {
                p.setStatus("DONE");
                repo.save(p);
            }
        }

        for (Patient p : patients) {
            if (p.isEmergency() && p.getStatus().equals("WAITING")) {
                p.setStatus("IN_PROGRESS");
                currentToken = p.getToken();
                return repo.save(p);
            }
        }

        for (Patient p : patients) {
            if (p.getStatus().equals("WAITING")) {
                p.setStatus("IN_PROGRESS");
                currentToken = p.getToken();
                return repo.save(p);
            }
        }

        return null;
    }

    public List<Patient> getAllPatients() {
        return repo.findAll();
    }

    public int getCurrentToken() {
        return currentToken;
    }

    public void setAvgTime(int avgTime) {
        this.avgTime = avgTime;
    }

    public void cancelPatient(Long id) {
        repo.deleteById(id);
    }

    public Patient getCurrentPatient() {
        return repo.findAll()
                .stream()
                .filter(p -> p.getStatus().equals("IN_PROGRESS"))
                .findFirst()
                .orElse(null);
    }
    public int getAvgTime() {
    return avgTime;
}
}