package com.transport.dataaccessobject;

import com.transport.domainobject.CarDO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * CarRepositoryTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest
{

    private static final Logger LOG = LoggerFactory.getLogger(CarRepositoryTest.class);

    @Autowired
    private CarRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    /**test findByLicensePlate
     * @throws Exception
     */
    @Test
    public void findByLicensePlate() throws Exception
    {
        LOG.info("CarRepositoryTest: GET :findByLicensePlate");
        CarDO savedCar = entityManager.persistFlushFind(new CarDO("DL1235", 2, true, 4, "cng", "bmw"));
        CarDO car = repository.findByLicensePlate("DL1235");
        Assertions.assertThat(car.getManufacturer()).isEqualTo(savedCar.getManufacturer());
    }
}
