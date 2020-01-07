package com.transport.service.car;

import com.transport.domainobject.CarDO;
import com.transport.exception.ConstraintsViolationException;
import com.transport.exception.EntityNotFoundException;

import java.util.List;

/**
 * CarService
 */
public interface CarService {

    /**
     * @param carId
     * @return
     * @throws EntityNotFoundException
     */
    CarDO find(Long carId) throws EntityNotFoundException;


    /**
     * @param carDO
     * @return
     * @throws ConstraintsViolationException
     */
    CarDO create(CarDO carDO) throws ConstraintsViolationException;


    /**
     * @param carId
     * @throws EntityNotFoundException
     */
    void delete(Long carId) throws EntityNotFoundException;


    /**
     * @param carDO
     * @throws EntityNotFoundException
     */
    void update(CarDO carDO) throws EntityNotFoundException;


    /**
     * @param carLicense
     * @return
     * @throws EntityNotFoundException
     */
    CarDO findCarByLicense(String carLicense) throws EntityNotFoundException;


    /**
     * @return List<CarDO>
     */
    List<CarDO> getAllCars();
}
