package com.transport.domainobject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * CarDO
 */
@Entity
@Table(name = "car", uniqueConstraints = @UniqueConstraint(name = "license_plate", columnNames = {"licensePlate"}))
public class CarDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "License can not be null!")
    private String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "Seat count can not be null!")
    private int seatCount;

    @Column(nullable = false)
    private boolean convertible;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    @NotNull(message = "Engine type can not be null!")
    private String engineType;

    @Column(nullable = false)
    @NotNull(message = "manufacturer can not be null!")
    private String manufacturer;

    public CarDO() {

    }


    public CarDO(
            String licensePlate, int seatCount, boolean convertible, int rating, String engineType,
            String manufacturer) {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getLicensePlate() {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    public int getSeatCount() {
        return seatCount;
    }


    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }


    public boolean isConvertible() {
        return convertible;
    }


    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }


    public int getRating() {
        return rating;
    }


    public void setRating(int rating) {
        this.rating = rating;
    }


    public String getEngineType() {
        return engineType;
    }


    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }


    public String getManufacturer() {
        return manufacturer;
    }


    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
