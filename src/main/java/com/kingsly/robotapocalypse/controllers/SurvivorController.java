package com.kingsly.robotapocalypse.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.kingsly.robotapocalypse.models.LocationCoordinates;
import com.kingsly.robotapocalypse.models.Survivor;
import com.kingsly.robotapocalypse.repositories.SurvivorRepository;

@RestController
@RequestMapping("/survivors")
public class SurvivorController {
    @Autowired
    private SurvivorRepository repo;

    @GetMapping
    public ResponseEntity<List<Survivor>> getSurvivors(
            @PageableDefault(size = 10, direction = Direction.ASC, sort = "name") Pageable pg) {
        return ResponseEntity.ok(repo.findAll(pg).toList());
    }

    @PostMapping
    public ResponseEntity<Void> createSurvivor(@RequestBody Survivor model, UriComponentsBuilder ucb) {
        boolean exists = model.getId() == null ? false : repo.existsById(model.getId().longValue());
        if (!exists) {
            Survivor savedSurvivor = repo.save(model);
            URI survivorLocation = ucb.path("survivors/{id}").buildAndExpand(savedSurvivor.getId()).toUri();
            return ResponseEntity.created(survivorLocation).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survivor> getSurvivor(@PathVariable Long id) {
        Optional<Survivor> survivor = id == null ? Optional.empty() : repo.findById(id);
        if (survivor.isPresent())
            return ResponseEntity.ok(survivor.get());
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/lati-{latitude}:long-{longitude}")
    public ResponseEntity<Void> updateSurvivorLocation(
            @PathVariable long id,
            @PathVariable float latitude,
            @PathVariable float longitude) {
        Optional<Survivor> survivorOpt = repo.findById(id);
        if (survivorOpt.isPresent()) {
            Survivor survivor = survivorOpt.get();
            survivor.setLastLocation(new LocationCoordinates(latitude, longitude));
            repo.save(survivor);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/flag/{id}")
    public ResponseEntity<Void> flagSurvivor(@PathVariable long id) {
        Optional<Survivor> survivorOptional = repo.findById(id);
        if (survivorOptional.isPresent()) {
            Survivor survivor = survivorOptional.get();
            Integer currentReports = survivor.getInfectionReports() + 1;
            survivor.setIsInfected(currentReports >= 3);
            survivor.setInfectionReports(currentReports);
            repo.save(survivor);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/percentage/infected")
    public ResponseEntity<Double> getInfectedPercentage() {
        List<Survivor> survivors = repo.findAll();
        List<Survivor> infectedSurvivors = survivors.stream().filter(s -> s.getIsInfected().equals(true)).toList();
        double survivorCount = survivors.size();
        double infectedCount = infectedSurvivors.size();
        double percentage = infectedCount / survivorCount * 100;
        return ResponseEntity.ok(percentage);
    }

    @GetMapping("/percentage/non-infected")
    public ResponseEntity<Double> getNonInfectedPercentage() {
        List<Survivor> survivors = repo.findAll();
        List<Survivor> nonInfectedSurvivors = survivors.stream().filter(s -> s.getIsInfected().equals(false)).toList();
        double survivorCount = survivors.size();
        double nonInfectedCount = nonInfectedSurvivors.size();
        double percentage = nonInfectedCount / survivorCount * 100;
        return ResponseEntity.ok(percentage);
    }

    @GetMapping("/infected")
    public ResponseEntity<List<Survivor>> getInfectedSurvivors() {
        List<Survivor> survivors = repo.findAll();
        List<Survivor> infectedSurvivors = survivors.stream().filter(s -> s.getIsInfected().equals(true)).toList();
        return ResponseEntity.ok(infectedSurvivors);
    }

    @GetMapping("/non-infected")
    public ResponseEntity<List<Survivor>> getNonInfectedSurvivors() {
        List<Survivor> survivors = repo.findAll();
        List<Survivor> nonInfectedSurvivors = survivors.stream().filter(s -> s.getIsInfected().equals(false)).toList();
        return ResponseEntity.ok(nonInfectedSurvivors);
    }
}
