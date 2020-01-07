package com.transport.service.driver;

import com.transport.domainobject.CarDO;
import com.transport.domainobject.DriverDO;
import com.transport.domainvalue.OnlineStatus;
import com.transport.exception.CarAlreadyInUseException;
import com.transport.exception.ConstraintsViolationException;
import com.transport.exception.EntityNotFoundException;

import java.util.List;

public interface DriverService {

    /**
     * @param driverId
     * @return
     * @throws EntityNotFoundException
     */
    DriverDO find(Long driverId) throws EntityNotFoundException;


    /**
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException
     */
    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;


    /**
     * @param driverId
     * @throws EntityNotFoundException
     */
    void delete(Long driverId) throws EntityNotFoundException;


    /**
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;


    /**
     * @param driverId
     * @param carDO
     * @return
     * @throws CarAlreadyInUseException
     * @throws EntityNotFoundException
     */
    DriverDO updateDriverCar(long driverId, CarDO carDO) throws CarAlreadyInUseException, EntityNotFoundException;


    /**
     * @param driverId
     * @param carDO
     * @return
     * @throws EntityNotFoundException
     */
    DriverDO removeDriverCar(long driverId, CarDO carDO) throws EntityNotFoundException;


    /**
     * @param onlineStatus
     * @return
     */
    List<DriverDO> find(OnlineStatus onlineStatus);


    /**
     * @return List<DriverDO>
     */
    List<DriverDO> findAllDrivers();

}
