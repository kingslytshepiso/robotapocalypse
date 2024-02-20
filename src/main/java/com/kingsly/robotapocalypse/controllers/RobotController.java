package com.kingsly.robotapocalypse.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.kingsly.robotapocalypse.models.Robot;

@RestController
@RequestMapping("/robots")
@SuppressWarnings("null")
public class RobotController {
    private WebClient client = WebClient.create("https://robotstakeover20210903110417.azurewebsites.net/robotcpu");

    @GetMapping
    public ResponseEntity<?> getRobots(Pageable pg, @RequestParam(name = "filter", required = false) String filter) {
        var results = client.get().retrieve().toEntity(Robot[].class).block().getBody();
        var filtered = filterList(filter, results);
        PagedListHolder<Robot> page = new PagedListHolder<Robot>();
        page.setSource(filtered);
        page.setPage(pg.getPageNumber());
        page.setPageSize(pg.getPageSize());
        return ResponseEntity.ok(page);
    }

    private List<Robot> filterList(String filter, Robot[] allRobots) {
        var filterColumns = filter.toString().split(":");
        List<Robot> filtered = new ArrayList<>();
        if (filterColumns.length == 2 &&
                !(filterColumns[0].isEmpty()) &&
                !(filterColumns[1].isEmpty())) {
            if (filterColumns[0].equals("model")) {
                for (Robot r : allRobots) {
                    if (r.getModel().equalsIgnoreCase(filterColumns[1])) {
                        filtered.add(r);
                    }
                }
            } else if (filterColumns[0].equals("serialNumber")) {
                for (Robot r : allRobots) {
                    if (r.getSerialNumber().equalsIgnoreCase(filterColumns[1])) {
                        filtered.add(r);
                    }
                }
            } else if (filterColumns[0].equals("manufacturedDate")) {
                for (Robot r : allRobots) {
                    if (r.getManufacturedDate().equalsIgnoreCase(filterColumns[1])) {
                        filtered.add(r);
                    }
                }
            } else if (filterColumns[0].equals("category")) {
                for (Robot r : allRobots) {
                    if (r.getCategory().equalsIgnoreCase(filterColumns[1])) {
                        filtered.add(r);
                    }
                }
            } else
                filtered = Arrays.asList(allRobots);
        } else
            filtered = Arrays.asList(allRobots);

        return filtered;
    }
}
