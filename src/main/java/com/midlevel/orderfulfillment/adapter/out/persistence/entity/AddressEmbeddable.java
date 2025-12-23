package com.midlevel.orderfulfillment.adapter.out.persistence.entity;

import com.midlevel.orderfulfillment.domain.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * JPA Embeddable for Address value object.
 * 
 * @Embeddable means this class's fields will be stored as columns
 * in the owning entity's table (orders table in this case).
 * 
 * This is the JPA way of handling value objects that don't need
 * their own table.
 */
@Embeddable
public class AddressEmbeddable {
    
    @Column(name = "street", nullable = false)
    private String street;
    
    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "state", nullable = false, length = 2)
    private String state;
    
    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;
    
    @Column(name = "country", nullable = false, length = 2)
    private String country;
    
    /**
     * JPA no-arg constructor.
     */
    protected AddressEmbeddable() {
    }
    
    /**
     * Constructor for creating AddressEmbeddable.
     */
    public AddressEmbeddable(String street, String city, String state,
                             String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }
    
    /**
     * Converts this embeddable to domain Address.
     */
    public Address toDomain() {
        return Address.of(street, city, state, zipCode, country);
    }
    
    /**
     * Creates embeddable from domain Address.
     */
    public static AddressEmbeddable fromDomain(Address address) {
        return new AddressEmbeddable(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry()
        );
    }
    
    // Getters and setters
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
}
