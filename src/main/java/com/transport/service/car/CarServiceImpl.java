package com.transport.service.car;

import com.transport.dataaccessobject.CarRepository;
import com.transport.domainobject.CarDO;
import com.transport.exception.ConstraintsViolationException;
import com.transport.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some car specific things.
 * <p/>
 */
@Service
public class CarServiceImpl implements CarService {

    private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    public CarServiceImpl(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    /**
     * select a car by Id
     *
     * @param carId
     * @return
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException {
        LOG.info("CarServiceImpl : find", carId);
        return findCarChecked(carId);
    }


    /**
     * Creates a new car.
     *
     * @param carDO
     * @return
     * @throws ConstraintsViolationException if a car already exists with the given License, ... .
     */
    @Override
    @Transactional
    public CarDO create(CarDO carDO) throws ConstraintsViolationException {
        LOG.info("CarServiceImpl : create", carDO);
        CarDO car;
        try {
            car = carRepository.save(carDO);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }


    /**
     * Delete car By ID
     *
     * @param carId
     */
    @Override
    public void delete(Long carId) {
        LOG.info("CarServiceImpl : delete", carId);
        carRepository.deleteById(carId);
    }


    @Override
    public void update(CarDO carDO) throws EntityNotFoundException {
        LOG.info("CarServiceImpl : update", carDO);
        carRepository.save(carDO);
    }


    @Override
    public CarDO findCarByLicense(String licensePlate) {
        LOG.info("CarServiceImpl : findCarByLicense", licensePlate);
        return carRepository.findByLicensePlate(licensePlate);
    }


    @Override
    public List<CarDO> getAllCars() {
        LOG.info("CarServiceImpl : getAllCars");
        return carRepository.getAllBy();
    }


    private CarDO findCarChecked(Long carId) throws EntityNotFoundException {
        LOG.info("CarServiceImpl : findCarChecked", carId);
        return carRepository
                .findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
    }
}
