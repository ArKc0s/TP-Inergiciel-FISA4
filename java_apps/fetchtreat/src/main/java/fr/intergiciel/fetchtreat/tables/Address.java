package fr.intergiciel.fetchtreat.tables;


import java.sql.Date;

public class Address {
    private int addressId;
    private int addressIndex;
    private String street;
    private String otherStreet;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String addressType;
    private Patient patient;

    public Address(int addressId, int addressIndex, String street, String otherStreet, String city, String state, String postalCode, String country, String addressType, Patient patient) {
        this.addressId = addressId;
        this.addressIndex = addressIndex;
        this.street = street;
        this.otherStreet = otherStreet;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.addressType = addressType;
        this.patient = patient;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getAddressIndex() {
        return addressIndex;
    }

    public void setAddressIndex(int addressIndex) {
        this.addressIndex = addressIndex;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getOtherStreet() {
        return otherStreet;
    }

    public void setOtherStreet(String otherStreet) {
        this.otherStreet = otherStreet;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressType() {
        return addressType;
    }
}
