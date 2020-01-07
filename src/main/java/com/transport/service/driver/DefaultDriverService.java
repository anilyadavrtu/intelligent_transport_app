package com.transport.service.driver;

import com.transport.dataaccessobject.DriverRepository;
import com.transport.domainobject.CarDO;
import com.transport.domainobject.DriverDO;
import com.transport.domainvalue.GeoCoordinate;
import com.transport.domainvalue.OnlineStatus;
import com.transport.exception.CarAlreadyInUseException;
import com.transport.exception.ConstraintsViolationException;
import com.transport.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    public DefaultDriverService(final DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
        DriverDO driver;
        try {
            driver = driverRepository.save(driverDO);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    @Override
    public DriverDO updateDriverCar(long driverId, CarDO carDO)
            throws CarAlreadyInUseException, EntityNotFoundException {

        DriverDO driverDO = findDriverChecked(driverId);
        DriverDO updatedDriverDO = null;
        DriverDO driverDOByCarDO = null;
        try {
            driverDOByCarDO = driverRepository.findDriverDOByCarDO(carDO);
            if (driverDOByCarDO != null) {
                throw new CarAlreadyInUseException("CarAlreadyInUseException while updateDriverCar");
            }
        } catch (DataIntegrityViolationException e) {
            LOG.warn("CarAlreadyInUseException while updateDriverCar: {}", carDO, e);
            throw new CarAlreadyInUseException(e.getMessage());
        }

        if (driverDO.getOnlineStatus().equals(OnlineStatus.ONLINE) && driverDO.getCarDO() == null) {
            driverDO.setCarDO(carDO);
            updatedDriverDO = driverRepository.save(driverDO);
        }

        return updatedDriverDO;
    }


    @Override
    public DriverDO removeDriverCar(long driverId, CarDO carDO) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCarDO(null);
        return driverRepository.save(driverDO);
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus) {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    @Override
    public List<DriverDO> findAllDrivers() {
        return driverRepository.findAll();

    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
        return driverRepository
                .findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

}
