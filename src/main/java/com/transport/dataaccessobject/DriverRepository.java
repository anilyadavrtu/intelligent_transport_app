package com.transport.dataaccessobject;

import com.transport.domainobject.CarDO;
import com.transport.domainobject.DriverDO;
import com.transport.domainvalue.OnlineStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long> {

    /**
     * @param onlineStatus
     * @return
     */
    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);


    /**
     * @param carDO
     * @return
     */
    DriverDO findDriverDOByCarDO(CarDO carDO);


    /**
     * @return findAll
     */
    List<DriverDO> findAll();

}
