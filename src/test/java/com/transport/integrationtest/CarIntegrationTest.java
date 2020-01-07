package com.transport.integrationtest;

import com.transport.datatransferobject.CarDTO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 *
 * CarIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class CarIntegrationTest
{

    private static final Logger LOG = LoggerFactory.getLogger(CarIntegrationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception
    {
        this.restTemplate = restTemplate.withBasicAuth("admin", "admin");
    }


    /**
     * test getCar
     */
    @Test
    public void getCar()
    {
        LOG.info("CarIntegrationTest: GET :getCar");
        ResponseEntity<CarDTO> response = restTemplate.getForEntity("/v1/cars/1", CarDTO.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getEngineType()).isEqualTo("cng");
        Assertions.assertThat(response.getBody().getLicensePlate()).isEqualTo("DL1234");
    }
}
