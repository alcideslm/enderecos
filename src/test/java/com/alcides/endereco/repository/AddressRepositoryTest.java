package com.alcides.endereco.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.alcides.endereco.model.Address;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AddressRepositoryTest {
    
    @Autowired private AddressRepository repository;

    @Test
    public void success_createAddress(){
        Address address = new Address();

        address.setCountry("Brasil");
        address.setState("Paraná");
        address.setCity("Cascavel");
        address.setStreetName("Rua 14 Bis");
        address.setNeighbourhood("Santos Dumont");
        address.setNumber("441");
        address.setZipcode("85804680");
        address.setLatitude("-24.9877122");
        address.setLongitude("-53.5131695");

        Address saved = repository.save(address);

        assertNotNull(saved);
    }

    @Test
    public void success_loadAddress(){
        Address address = new Address();

        address.setCountry("Brasil");
        address.setState("Paraná");
        address.setCity("Cascavel");
        address.setStreetName("Rua 14 Bis");
        address.setNeighbourhood("Santos Dumont");
        address.setNumber("441");
        address.setZipcode("85804680");
        address.setLatitude("-24.9877122");
        address.setLongitude("-53.5131695");

        Address saved = repository.save(address);

        Optional<Address> loadAddress = repository.findById(saved.getId());

        assertTrue(loadAddress.isPresent());
        assertTrue(saved.sameAddress(loadAddress.get()));
    }
}