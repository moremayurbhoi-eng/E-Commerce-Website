package com.example.demo.dto;

import com.example.demo.entity.Address;

public class OrderRequest {

    private Address address;

    public OrderRequest() {
        super();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}