package com.kingsly.robotapocalypse.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.kingsly.robotapocalypse.models.Robot;

@RestController
@RequestMapping("/robots")
public class RobotController {
    private WebClient client = WebClient.create("https://robotstakeover20210903110417.azurewebsites.net/robotcpu");

    @GetMapping
    public ResponseEntity<Set<Robot>> getRobots(
            @PageableDefault(size = 10, sort = "modelNumber", page = 1) Pageable pg) {
        var results = client.get().retrieve().toEntity(Robot[].class).block().getBody();
        Set<Robot> robotSet = new HashSet<>(Arrays.asList(results));
        return ResponseEntity.ok(robotSet);
    }
}
