package com.transport.integrationtest;

import com.transport.datatransferobject.DriverDTO;
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
 * DriverIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class DriverIntegrationTest
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
     * Test findDriver
     */
    @Test
    public void findDriver()
    {
        LOG.info("DriverIntegrationTest: GET :findDriver");
        ResponseEntity<DriverDTO> response = restTemplate.getForEntity("/v1/drivers/1", DriverDTO.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getUsername()).isEqualTo("driver01");
        Assertions.assertThat(response.getBody().getPassword()).isEqualTo("driver01pw");
    }
}
