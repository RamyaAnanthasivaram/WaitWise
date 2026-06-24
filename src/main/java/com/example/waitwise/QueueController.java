package com.example.waitwise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class QueueController {

    @Autowired
    private QueueService service;

    @PostMapping("/add")
    public Patient addPatient(@RequestParam String name,
                              @RequestParam boolean emergency) {
        return service.addPatient(name, emergency);
    }

    @PostMapping("/next")
    public Patient callNext() {
        return service.callNext();
    }

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return service.getAllPatients();
    }

    @GetMapping("/current")
    public int currentToken() {
        return service.getCurrentToken();
    }

    @PostMapping("/avg/{time}")
    public String setAvg(@PathVariable int time) {
        service.setAvgTime(time);
        return "Updated";
    }
    @GetMapping("/current-patient")
public Patient currentPatient() {
    return service.getAllPatients()
            .stream()
            .filter(p -> p.getStatus().equals("IN_PROGRESS"))
            .findFirst()
            .orElse(null);
}
@DeleteMapping("/cancel/{id}")
public String cancelPatient(@PathVariable Long id){
    service.cancelPatient(id);
    return "Cancelled";
}
@GetMapping("/avg")
public int getAvgTime() {
    return service.getAvgTime();
}
}