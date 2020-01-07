package com.transport.dataaccessobject;

import com.transport.domainobject.CarDO;
import com.transport.domainobject.DriverDO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit Test
 * DriverRepositoryTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DriverRepositoryTest
{

    private static final Logger LOG = LoggerFactory.getLogger(CarRepositoryTest.class);

    @MockBean
    private DriverRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    /**
     * findDriverDOByCarDO
     * @throws Exception
     */
    @Test
    public void findDriverDOByCarDO() throws Exception
    {
        LOG.info("DriverRepositoryTest: GET :findDriverDOByCarDO");
        CarDO carDO = new CarDO("DL123467", 2, true, 4, "cng", "bmw");
        DriverDO savedDriver = entityManager.persistFlushFind(new DriverDO("driver11", "driver11", carDO));
        DriverDO driverDO = repository.findDriverDOByCarDO(carDO);
        Assertions.assertThat(savedDriver.getCarDO().getManufacturer()).isEqualTo(driverDO.getCarDO().getManufacturer());
    }
}
