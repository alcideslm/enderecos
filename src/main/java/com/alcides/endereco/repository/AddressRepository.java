package com.alcides.endereco.repository;

import com.alcides.endereco.model.Address;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}