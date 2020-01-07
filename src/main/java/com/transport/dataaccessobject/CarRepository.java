package com.transport.dataaccessobject;

import com.transport.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Database Access Object for car table.
 * <p/>
 */
public interface CarRepository extends CrudRepository<CarDO, Long> {

    /**
     * getAllBy
     *
     * @return List<CarDO>
     */
    List<CarDO> getAllBy();


    /**
     * @param carLicense
     * @return
     */
    CarDO findByLicensePlate(String carLicense);

}
