package com.kingsly.robotapocalypse.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.kingsly.robotapocalypse.models.Gender;
import com.kingsly.robotapocalypse.models.LocationCoordinates;
import com.kingsly.robotapocalypse.models.Quantity;
import com.kingsly.robotapocalypse.models.Resource;
import com.kingsly.robotapocalypse.models.Survivor;
import com.kingsly.robotapocalypse.repositories.SurvivorRepository;

@Configuration
public class DataInitializer implements ApplicationRunner {
        @Autowired
        private SurvivorRepository repo;

        public void run(ApplicationArguments args) {
                Survivor survivor1 = new Survivor(
                                9773658295716l,
                                "Kyle",
                                "Hollard",
                                28,
                                Gender.MALE,
                                new LocationCoordinates(71.33F, 33.44F),
                                0,
                                false,
                                new ArrayList<>());
                var survivor1Res = Arrays.asList(
                                new Resource(null, "Water", "Food", new Quantity(2d, "Litres"), null),
                                new Resource(null, "Wood", "Material", new Quantity(10d, "Count"), null));
                survivor1.setResources(survivor1Res);
                Survivor survivor2 = new Survivor(
                                9973998295715l,
                                "Joeleen",
                                "Stevens",
                                26,
                                Gender.FEMALE,
                                new LocationCoordinates(38.33F, 76.44F),
                                1,
                                false,
                                new ArrayList<>());
                var survivor2Res = Arrays.asList(
                                new Resource(null, "Pain Killers", "Medicine", new Quantity(300d, "grams"), null));
                survivor2.setResources(survivor2Res);
                List<Survivor> survivors = Arrays.asList(survivor1, survivor2);
                repo.saveAll(survivors);
        }
}
