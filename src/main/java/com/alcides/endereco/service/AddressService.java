package com.alcides.endereco.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import com.alcides.endereco.api.GoogleMapsApi.GoogleMapsApi;
import com.alcides.endereco.model.Address;
import com.alcides.endereco.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class AddressService {

    @Autowired private AddressRepository repository;
	@Autowired private GoogleMapsApi mapsApi;

	public List<Address> listAll() {
		return repository.findAll();
	}

	public Optional<Address> loadById(Long id) {
		if(Objects.isNull(id))
			return Optional.empty();
			
		return repository.findById(id);
	}

	public Address create(Address address) {
		if (!address.hasLatLng())
			mapsApi.getAddressCoordinates(address);

		return repository.save(address);
	}

	public Address update(@Valid Address address) {
		Address oldAddress = repository.getById(address.getId());

		boolean isSameLatLong = oldAddress.sameLatLng(address.getLatitude(), address.getLongitude());
		
		/* Update coordinates when coordinates is null or only address changed */
		if(!address.hasLatLng() || (isSameLatLong && !oldAddress.sameAddress(address))) {
			address = mapsApi.getAddressCoordinates(address);
		}

		return repository.save(address);
	}

	public void delete(Long id) {
		Optional<Address> opAddress = repository.findById(id);

		if(opAddress.isEmpty()) {
			throw new NotFoundException("Identificador inválido, não foi possível realizar exclusão");
		}

		Address address = opAddress.get();
		address.setDeleted(Boolean.TRUE);

		repository.save(address);
	}
    
}