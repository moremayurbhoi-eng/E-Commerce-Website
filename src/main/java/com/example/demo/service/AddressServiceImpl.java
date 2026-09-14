package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    // Constructor
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // ==========================================
    // CREATE ADDRESS
    // ==========================================

    @Override
    public Address saveAddress(Address address) {

        return addressRepository.save(address);
    }

    // ==========================================
    // GET ALL ADDRESSES
    // ==========================================

    @Override
    public List<Address> getAllAddresses() {

        return addressRepository.findAll();
    }

    // ==========================================
    // GET ADDRESS BY ID
    // ==========================================

    @Override
    public Address getAddressById(Long id) {

        return addressRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Address not found with id: " + id
                    )
                );
    }

    // ==========================================
    // UPDATE ADDRESS
    // ==========================================

    @Override
    public Address updateAddress(
            Long id,
            Address address) {

        Address existingAddress =
                addressRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Address not found with id: " + id
                    )
                );

        existingAddress.setFullName(
                address.getFullName()
        );

        existingAddress.setPhone(
                address.getPhone()
        );

        existingAddress.setAddress(
                address.getAddress()
        );

        existingAddress.setCity(
                address.getCity()
        );

        existingAddress.setState(
                address.getState()
        );

        existingAddress.setPincode(
                address.getPincode()
        );

        existingAddress.setCountry(
                address.getCountry()
        );

        return addressRepository.save(existingAddress);
    }

    // ==========================================
    // DELETE ADDRESS
    // ==========================================

    @Override
    public void deleteAddress(Long id) {

        Address address =
                addressRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Address not found with id: " + id
                    )
                );

        addressRepository.delete(address);
    }
}