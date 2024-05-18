package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
