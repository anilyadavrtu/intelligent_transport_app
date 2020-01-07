package com.transport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transport.domainobject.CarDO;
import com.transport.domainobject.DriverDO;
import com.transport.domainvalue.OnlineStatus;
import com.transport.service.car.CarService;
import com.transport.service.driver.DriverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * DriverController Unit Test
 * DriverControllerTest  to test all DriverController methods
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest
{

    private static final Logger LOG = LoggerFactory.getLogger(DriverControllerTest.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @MockBean
    private CarService carService;

    /**Test getDriver
     * @throws Exception
     */
    @WithMockUser("USER")
    @Test
    public void getDriver() throws Exception
    {
        LOG.info("DriverControllerTest: GET :getDriver");
        CarDO carDO = new CarDO("DL1234", 2, true, 4, "cng", "bmw");
        given((driverService.find(1L))).willReturn(new DriverDO("driver1", "driver1", carDO));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/v1/drivers/1")).andExpect(status().isOk())
            .andExpect(jsonPath("username").value("driver1")).andExpect((jsonPath("password").value("driver1")));
    }


    /**
     * Test createDriver
     * @throws Exception
     */
    @WithMockUser("USER")
    @Test
    public void createDriver() throws Exception
    {
        LOG.info("DriverControllerTest: create :createDriver");
        CarDO carDO = new CarDO("DL1234", 2, true, 4, "cng", "bmw");
        DriverDO driverDO = new DriverDO("driver1", "driver1", carDO);
        when(driverService.create(any(DriverDO.class))).thenReturn(driverDO);
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/v1/drivers")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON).content(OBJECT_MAPPER.writeValueAsString(driverDO))
                    .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                    .param(csrfToken.getParameterName(), csrfToken.getToken()))

            .andExpect(status().isCreated())
            .andExpect(jsonPath("username").value("driver1")).andExpect((jsonPath("password").value("driver1")));
    }


    /**
     * Test deleteDriver
     * @throws Exception
     */
    @WithMockUser("USER")
    @Test
    public void deleteDriver() throws Exception
    {
        LOG.info("DriverControllerTest: delete :deleteDriver");
        doNothing().when(driverService).delete(1L);
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .delete("/v1/drivers/1")
                    .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                    .param(csrfToken.getParameterName(), csrfToken.getToken()))
            .andExpect(status().isOk());
    }


    /** Test findDrivers
     * @throws Exception
     */
    @WithMockUser("USER")
    @Test
    public void findDrivers() throws Exception
    {
        LOG.info("DriverControllerTest: GET :findDrivers");
        CarDO carDO = new CarDO("DL1234", 2, true, 4, "cng", "bmw");
        DriverDO driverDO = new DriverDO("driver1", "driver1", carDO);
        driverDO.setOnlineStatus(OnlineStatus.ONLINE);
        List<DriverDO> driverDOS = Arrays.asList(driverDO);
        when(driverService.findAllDrivers()).thenReturn(driverDOS);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drivers/").param("onlineStatus", "ONLINE")).andExpect(status().isOk());

    }

}
