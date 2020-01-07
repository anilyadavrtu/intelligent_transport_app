package com.transport.controller;

import com.transport.controller.mapper.CarMapper;
import com.transport.datatransferobject.CarDTO;
import com.transport.domainobject.CarDO;
import com.transport.exception.ConstraintsViolationException;
import com.transport.exception.EntityNotFoundException;
import com.transport.service.car.CarService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author anil
 */
@RestController
@RequestMapping("v1/cars")
public class CarController {

    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);

    private CarService carService;

    @Autowired
    public CarController(final CarService carService) {
        this.carService = carService;
    }


    @ApiOperation(value = "Get car by ID")
    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable long carId) throws EntityNotFoundException {
        LOG.info("GET :getCar", carId);
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    @ApiOperation(value = "Add car")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException {
        LOG.info("POST :createCar", carDTO);
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }

    @ApiOperation(value = "Update car")
    @PutMapping("/{carId}")
    public void updateCar(@PathVariable Long carId, @RequestBody CarDTO carDTO) throws EntityNotFoundException {
        LOG.info("PUT :updateCar", carId, carDTO);
        CarDO carDO = carService.find(carId);
        if (carDO != null) {
            carDO.setManufacturer(carDTO.getManufacturer());
            carDO.setRating(carDTO.getRating());
            carDO.setLicensePlate(carDTO.getLicensePlate());
            carDO.setEngineType(carDTO.getEngineType());
            carDO.setSeatCount(carDO.getSeatCount());
            carService.update(carDO);
        }

    }

    @ApiOperation(value = "Delete Car")
    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException {
        LOG.info("DELETE :deleteCar", carId);
        carService.delete(carId);
    }

    @ApiOperation(value = "Get all cars")
    @GetMapping
    public List<CarDTO> allCars() {
        LOG.info("GET :allCars");
        return CarMapper.makeCarDTOList(carService.getAllCars());
    }
}
