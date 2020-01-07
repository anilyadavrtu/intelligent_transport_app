package com.transport.controller;

import com.transport.controller.mapper.DriverMapper;
import com.transport.datatransferobject.DriverDTO;
import com.transport.domainobject.DriverDO;
import com.transport.domainvalue.OnlineStatus;
import com.transport.exception.CarAlreadyInUseException;
import com.transport.exception.ConstraintsViolationException;
import com.transport.exception.EntityNotFoundException;
import com.transport.service.car.CarService;
import com.transport.service.driver.DriverService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anil
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController {

    private static final Logger LOG = LoggerFactory.getLogger(DriverController.class);

    private final DriverService driverService;

    private final CarService carService;

    @Autowired
    public DriverController(final DriverService driverService, CarService carService) {
        this.driverService = driverService;
        this.carService = carService;
    }

    @ApiOperation(value = "Get driver by ID")
    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException {
        LOG.info("GET :getDriver", driverId);
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }

    @ApiOperation(value = "Add driver")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException {
        LOG.info("CREATED :createDriver");
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }

    @ApiOperation(value = "Delete driver")
    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException {
        LOG.info("delete :deleteDriver");
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    @ApiOperation(value = "Update Location")
    public void updateLocation(
            @PathVariable long driverId, @RequestParam double longitude,
            @RequestParam double latitude)
            throws EntityNotFoundException {
        LOG.info("put :updateLocation");
        driverService.updateLocation(driverId, longitude, latitude);
    }

    @ApiOperation(value = "Update driver and car association")
    @PutMapping("/updateDriverCar")
    public DriverDO updateDriverCar(@RequestParam long driverId, @RequestParam String licenseNumber)
            throws EntityNotFoundException, CarAlreadyInUseException {
        LOG.info("put :updateDriverCar");
        return driverService.updateDriverCar(driverId, carService.findCarByLicense(licenseNumber));
    }

    @ApiOperation(value = "remove driver and car association Car")
    @PutMapping("/removeDriverCar")
    public DriverDO removeDriverCar(@RequestParam long driverId, @RequestParam String licenseNumber)
            throws EntityNotFoundException {
        LOG.info("put :removeDriverCar");
        return driverService.removeDriverCar(driverId, carService.findCarByLicense(licenseNumber));
    }

    @ApiOperation(value = "Find all driver")
    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus) {
        LOG.info("GET :findDrivers");
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }

    @ApiOperation(value = "Find driver with Online, Car No and with driver ")
    @GetMapping("/advanceSearch")
    public List<DriverDO> advanceSearch(@RequestParam String query) throws EntityNotFoundException {
        LOG.info("GET :advanceSearch");
        List<DriverDO> allDrivers = driverService.findAllDrivers();
        List<DriverDO> driverDOS = new ArrayList<>();
        for (DriverDO driverDO : allDrivers) {

            if (driverDO.getUsername().equalsIgnoreCase(query) || driverDO.getOnlineStatus().toString().equalsIgnoreCase(query)) {
                driverDOS.add(driverDO);
            } else if (driverDO.getCarDO() != null) {
                if (driverDO.getCarDO().getLicensePlate().equalsIgnoreCase(query)
                        || String.valueOf(driverDO.getCarDO().getSeatCount()).equalsIgnoreCase(query) || driverDO
                        .getCarDO()
                        .getEngineType().equalsIgnoreCase(query)
                        || driverDO
                        .getCarDO().getManufacturer()
                        .equalsIgnoreCase(query)
                        || driverDO.getCarDO().getRating() == Integer.valueOf(query)) {
                    driverDOS.add(driverDO);
                }

            }
        }
        return driverDOS;
    }
}
