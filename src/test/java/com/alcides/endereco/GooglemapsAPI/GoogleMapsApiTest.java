package com.alcides.endereco.GooglemapsAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alcides.endereco.api.GoogleMapsApi.GoogleMapsApi;
import com.alcides.endereco.model.Address;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoogleMapsApiTest {
    @Autowired private GoogleMapsApi googleMapsApi;

    @Test
    public void checkCoordinates_getAddressCoordinates(){
        Address address = new Address();
        address.setCountry("Brasil");
        address.setState("Paraná");
        address.setCity("Cascavel");
        address.setStreetName("Rua 14 Bis");
        address.setNumber("441");

        address = googleMapsApi.getAddressCoordinates(address);

        String lat = "-24.9877122";
        String lng = "-53.5131695";
        assertEquals(lat, address.getLatitude());
        assertEquals(lng, address.getLongitude());
    }
}