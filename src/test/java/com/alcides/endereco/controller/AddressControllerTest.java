package com.alcides.endereco.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import com.alcides.endereco.model.Address;
import com.alcides.endereco.service.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;

@WebMvcTest
public class AddressControllerTest {
    @Autowired
    private AddressController controller;
    @MockBean
    private AddressService service;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.controller);
    }

    private String getJson(Address address) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(address);
    }

    @Test
    public void loadById_sucess() {
        when(this.service.loadById(1L)).thenReturn(Optional.of(new Address()));

        given().accept(ContentType.JSON).when().get("/1").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void loadById_notFound() {
        when(this.service.loadById(1L)).thenReturn(Optional.empty());

        given().accept(ContentType.JSON).when().get("/1").then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void listAll_returnSucess() {
        when(this.service.listAll()).thenReturn(new ArrayList<Address>());

        given().accept(ContentType.JSON).when().get("/listAll").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void createAddress_created() {
        Address address = new Address();
        address.setStreetName("Rua 14 Bis");
        address.setNumber("441");
        address.setNeighbourhood("Santos Dumont");
        address.setCity("Cascavel");
        address.setState("Paraná");
        address.setCountry("Brasil");
        address.setZipcode("85800000");

        when(this.service.create(address)).thenReturn(new Address());

        String json = "";
        
        try {
            json = getJson(address);
        } catch (JsonProcessingException e) {
            assertTrue(false);
        }

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(json)
            .when().post()
            .then().statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void createAddress_emptyValueBadRequest(){
        Address address = new Address();

        when(this.service.create(address)).thenReturn(new Address());

        String jsonEmpty = "";

        try {
            jsonEmpty = getJson(address);
        } catch (JsonProcessingException e) {
            assertTrue(false);
        }

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(jsonEmpty)
            .when().post()
            .then().statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void createAddress_idNotNullBadRequest(){
        Address address = new Address();

        address.setId(1l);
        address.setStreetName("Rua 14 Bis");
        address.setNumber("441");
        address.setNeighbourhood("Santos Dumont");
        address.setCity("Cascavel");
        address.setState("Paraná");
        address.setCountry("Brasil");
        address.setZipcode("85800000");

        when(this.service.create(address)).thenReturn(new Address());

        String json = "";

        try {
            json = getJson(address);
        } catch (JsonProcessingException e) {
            assertTrue(false);
        }

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(json)
            .when().post()
            .then().statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void updateAddress_sucess(){
        Address address = new Address();

        address.setId(1l);
        address.setStreetName("Rua 14 Bis");
        address.setNumber("441");
        address.setNeighbourhood("Santos Dumont");
        address.setCity("Cascavel");
        address.setState("Paraná");
        address.setCountry("Brasil");
        address.setZipcode("85800000");

        when(this.service.update(address)).thenReturn(address);
        when(this.service.loadById(1l)).thenReturn(Optional.of(address));

        String json = "";

        try {
            json = getJson(address);
        } catch (JsonProcessingException e) {
            assertTrue(false);
        }

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(json)
            .when().put()
            .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updateAddress_BadRequest(){
        Address address = new Address();

        address.setId(1l);
        address.setStreetName("Rua 14 Bis");
        address.setNumber("441");
        address.setNeighbourhood("Santos Dumont");
        address.setCity("Cascavel");
        address.setState("Paraná");
        address.setCountry("Brasil");
        address.setZipcode("85800000");

        when(this.service.update(address)).thenReturn(address);
        when(this.service.loadById(1l)).thenReturn(Optional.empty());

        String json = "";

        try {
            json = getJson(address);
        } catch (JsonProcessingException e) {
            assertTrue(false);
        }

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(json)
            .when().put()
            .then().statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void delete_sucess() {
        when(this.service.loadById(1L)).thenReturn(Optional.of(new Address()));

        given().accept(ContentType.JSON).when().delete("/1").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void delete_notFound() {
        when(this.service.loadById(1L)).thenReturn(Optional.empty());

        given().accept(ContentType.JSON).when().delete("/1").then().statusCode(HttpStatus.NOT_FOUND.value());
    }   
}