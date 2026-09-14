package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Address;

public interface AddressService {

    Address saveAddress(Address address);

    List<Address> getAllAddresses();

    Address getAddressById(Long id);

    Address updateAddress(Long id, Address address);

    void deleteAddress(Long id);
}