package com.kingsly.robotapocalypse.controllerTests;

import java.util.List;
import java.net.URI;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kingsly.robotapocalypse.models.Gender;
import com.kingsly.robotapocalypse.models.LocationCoordinates;
import com.kingsly.robotapocalypse.models.Quantity;
import com.kingsly.robotapocalypse.models.Resource;
import com.kingsly.robotapocalypse.models.Survivor;
import com.kingsly.robotapocalypse.repositories.SurvivorRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SurvivorControllerTests {

    @Autowired
    private SurvivorRepository repo;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private String baseUrl = "http://localhost:8080/survivors";
    private List<Survivor> availableSurvivors;

    @BeforeAll
    public void beforeAll() {
        List<Survivor> survivors = new ArrayList<>();
        survivors.add(new Survivor(
                101l,
                "John",
                "Doe",
                28,
                Gender.MALE,
                new LocationCoordinates(71.33F, 33.44F),
                0,
                false,
                new ArrayList<>()));
        availableSurvivors = repo.saveAll(survivors);
    }

    @BeforeEach
    public void beforeEach() {
    }

    @AfterAll
    public void afterAll() {

    }

    @Test
    @DisplayName("Test the request to get all survivors")
    void getAllSurvivorsTest() {
        ResponseEntity<Survivor[]> response = restTemplate.getForEntity(baseUrl, Survivor[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Test the request to post a survivor that already exists in the database")
    void postASurvivorThatAlreadyExistsTest() {

        assumeFalse(availableSurvivors.isEmpty());

        Survivor toAdd = availableSurvivors.getLast();
        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl, toAdd, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @DisplayName("Test the request to post a survivor with a success outcome after verifying the existence of the newly created survivor")
    void postASurvivor() {
        Survivor toAdd = new Survivor(
                102l,
                "Jane",
                "Doe",
                31,
                Gender.FEMALE,
                new LocationCoordinates(77.65F, 84.03F),
                0,
                false,
                new ArrayList<>());
        ResponseEntity<Void> postResponse = restTemplate.postForEntity(baseUrl, toAdd, Void.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assumeFalse(postResponse.getHeaders().getLocation() == null);

        String survivorLocation = postResponse.getHeaders().getLocation().toString();
        ResponseEntity<Survivor> getSavedResponse = restTemplate.getForEntity(survivorLocation, Survivor.class);
        assertThat(getSavedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Survivor[]> getAllResponse = restTemplate.getForEntity(baseUrl, Survivor[].class);
        assertThat(getAllResponse.getBody()).contains(getSavedResponse.getBody());
    }

    @Test
    @DisplayName("Test the request to post a survivor with resources")
    void postASurvivorWithResources() {
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource(null, "Water", "Food", new Quantity(3d, "litres"), null));
        resources.add(new Resource(null, "Pain Killers", "Medicine", new Quantity(1d, "kilograms"), null));
        Survivor toAdd = new Survivor(
                103l,
                "Jane",
                "Doe",
                31,
                Gender.FEMALE,
                new LocationCoordinates(77.65F, 84.03F),
                0,
                false,
                null);
        toAdd.setResources(resources);
        ResponseEntity<Void> postResponse = restTemplate.postForEntity(baseUrl, toAdd, Void.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Test the request to update a survivor last location for a survivor that does not exist")
    void updateSurvivorLocationRequestForASurvivorThatDoesNotExistTest() {
        String updateUri = baseUrl + "/500/lati-89.09:long-75.009";
        ResponseEntity<Void> updateResponse = restTemplate
                .exchange(URI.create(updateUri), HttpMethod.PUT, null, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Test the request to update a survivor last location")
    void updateSurvivorLastLocation() {
        String updateUri = baseUrl + "/101/lati-89.09:long-75.009";
        ResponseEntity<Void> updateResponse = restTemplate
                .exchange(URI.create(updateUri), HttpMethod.PUT, null, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Test the request to flag/report a survivor infection using a non-existent id")
    void flagRequestWithANonExistentSurvivorTest() {
        String flagUrl = baseUrl + "/flag/500";
        ResponseEntity<Void> response = restTemplate
                .exchange(flagUrl, HttpMethod.PUT, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Test the request to flag/report a survivor infection with a success expected outcome")
    void flagSurvivorRequestTest() {

        ResponseEntity<Survivor> getInitResponse = restTemplate
                .getForEntity(baseUrl + "/101", Survivor.class);
        assertThat(getInitResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assumeFalse(getInitResponse.getBody() == null);
        Integer initialReports = getInitResponse.getBody().getInfectionReports();

        String flagUrl = baseUrl + "/flag/101";
        ResponseEntity<Void> flagResponse = restTemplate
                .exchange(flagUrl, HttpMethod.PUT, null, Void.class);
        assertThat(flagResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Survivor> getFinalResponse = restTemplate
                .getForEntity(baseUrl + "/101", Survivor.class);
        assumeTrue(getFinalResponse.getStatusCode().equals(HttpStatus.OK));
        assertThat(getFinalResponse.getBody().getInfectionReports()).isGreaterThan(initialReports);
    }

    @Test
    @RepeatedTest(3)
    @DisplayName("Test the request to flag/report a survivor three times to verify the infection status change")
    void flagSurvivorRepeatedToVerifyInfectionStatusChangeTest() {
        String flagUrl = baseUrl + "/flag/102";
        ResponseEntity<Void> flagResponse = restTemplate
                .exchange(flagUrl, HttpMethod.PUT, null, Void.class);
        assertThat(flagResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Survivor> getResponse = restTemplate
                .getForEntity(baseUrl + "/102", Survivor.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assumeTrue(getResponse.getBody().getInfectionReports() >= 3);
        assertThat(getResponse.getBody().getIsInfected()).isTrue();
    }
}
