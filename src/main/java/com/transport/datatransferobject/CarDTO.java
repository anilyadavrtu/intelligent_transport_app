package com.transport.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * CarDTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message = "License can not be null!")
    private String licensePlate;

    @NotNull(message = "Seat count can not be null!")
    private int seatCount;

    private boolean convertible;

    private int rating;

    @NotNull(message = "Engine type can not be null!")
    private String engineType;

    @NotNull(message = "manufacturer can not be null!")
    private String manufacturer;

    private CarDTO() {
    }

    public CarDTO(Long id, String licensePlate, int seatCount, boolean convertible, int rating, String engineType,
                  String manufacturer) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }

    public static CarDTOBuilder newBuilder() {

        return new CarDTOBuilder();

    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getSeatCount() {
        return seatCount;
    }


    public boolean isConvertible() {
        return convertible;
    }


    public int getRating() {
        return rating;
    }

    public String getEngineType() {
        return engineType;
    }


    public String getManufacturer() {
        return manufacturer;
    }


    public static class CarDTOBuilder {

        private Long id;

        private String licensePlate;

        private int seatCount;

        private boolean convertible;

        private int rating;

        private String engineType;

        private String manufacturer;

        public CarDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CarDTOBuilder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarDTOBuilder setSeatCount(int seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public CarDTOBuilder setConvertible(boolean convertible) {
            this.convertible = convertible;
            return this;
        }

        public CarDTOBuilder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public CarDTOBuilder setEngineType(String engineType) {
            this.engineType = engineType;
            return this;
        }

        public CarDTOBuilder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public CarDTO createCarDTO() {
            return new CarDTO(id, licensePlate, seatCount, convertible, rating, engineType, manufacturer);
        }
    }
}
