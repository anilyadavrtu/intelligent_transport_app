package com.transport.service.car;

import com.transport.dataaccessobject.CarRepository;
import com.transport.domainobject.CarDO;
import com.transport.integrationtest.CarIntegrationTest;
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
 *CarServiceImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest
{

    private static final Logger LOG = LoggerFactory.getLogger(CarIntegrationTest.class);

    @Mock
    private CarRepository carRepository;

    private CarServiceImpl carService;

    @Before
    public void SetUp()
    {
        carService = new CarServiceImpl(carRepository);
    }


    @Test
    @WithMockUser(username = "driver", password = "driver", roles = "USER")
    public void findByLicensePlate() throws Exception
    {
        LOG.info("CarServiceImplTest: GET :findByLicensePlate");
        given(carRepository.findByLicensePlate("DL1234"))
            .willReturn(
                new CarDO(
                    "DL1234", 2, true, 4, "cng",
                    "bmw"));
        CarDO carDO = carRepository.findByLicensePlate("DL1234");
        Assertions.assertThat(carDO.getManufacturer()).isEqualTo("bmw");

    }
}
