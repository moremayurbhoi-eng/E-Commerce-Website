package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Address;
import com.example.demo.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // CREATE ADDRESS
    @PostMapping
    public ResponseEntity<Address> saveAddress(
            @RequestBody Address address) {

        return ResponseEntity.ok(
                addressService.saveAddress(address)
        );
    }

    // GET ALL ADDRESSES
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {

        return ResponseEntity.ok(
                addressService.getAllAddresses()
        );
    }

    // GET ADDRESS BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                addressService.getAddressById(id)
        );
    }

    // UPDATE ADDRESS
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable Long id,
            @RequestBody Address address) {

        return ResponseEntity.ok(
                addressService.updateAddress(id, address)
        );
    }

    // DELETE ADDRESS
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long id) {

        addressService.deleteAddress(id);

        return ResponseEntity.ok(
                "Address deleted successfully"
        );
    }
}