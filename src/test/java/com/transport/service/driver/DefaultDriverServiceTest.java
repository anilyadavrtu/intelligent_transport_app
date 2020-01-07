package com.transport.service.driver;

import com.transport.dataaccessobject.DriverRepository;
import com.transport.domainobject.CarDO;
import com.transport.domainobject.DriverDO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.BDDMockito.given;

/**
 * DefaultDriverServiceTest
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultDriverServiceTest
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverServiceTest.class);

    @Mock
    private DriverRepository driverRepository;

    private DefaultDriverService driverService;

    @Before
    public void SetUp()
    {
        driverService = new DefaultDriverService(driverRepository);
    }


    /**
     * @throws Exception findDriverDOByCarDO
     */
    @Test
    @WithMockUser(username = "driver", password = "driver", roles = "USER")
    public void findDriverDOByCarDO() throws Exception
    {
        LOG.info("DefaultDriverServiceTest: GET :findDriverDOByCarDO");
        CarDO carDO = new CarDO("DL1234", 2, true, 4, "cng", "bmw");
        given(driverRepository.findDriverDOByCarDO(carDO)).willReturn(new DriverDO("driver1", "driver1", carDO));
        DriverDO driverDO = driverRepository.findDriverDOByCarDO(carDO);
        Assertions.assertThat(carDO.getManufacturer()).isEqualTo("bmw");

    }
}
