package com.transport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transport.domainobject.CarDO;
import com.transport.service.car.CarService;
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
 * CarController Unit Test
 * CarController to test carController Methods
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest
{

    private static final Logger LOG = LoggerFactory.getLogger(CarControllerTest.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    /**getCar
     * @throws Exception
     */
    @Test
    @WithMockUser("ADMIN")
    public void getCar() throws Exception
    {
        LOG.info("CarControllerTest: GET :getCar");
        given(carService.find(1L)).willReturn(new CarDO("DL1234", 2, true, 4, "cng", "bmw"));
        mockMvc
            .perform(MockMvcRequestBuilders.get("/v1/cars/1")).andExpect(status().isOk())
            .andExpect(jsonPath("engineType").value("cng")).andExpect((jsonPath("licensePlate").value("DL1234")));

    }


    /**
     * createCar
     * @throws Exception
     */
    @Test
    @WithMockUser("ADMIN")
    public void createCar() throws Exception
    {
        LOG.info("CarControllerTest: post :createCar");
        CarDO carDO = new CarDO("HR1234", 2, true, 4, "cng", "bmw");
        when(carService.create(any(CarDO.class))).thenReturn(carDO);
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/v1/cars")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON).content(OBJECT_MAPPER.writeValueAsString(carDO))
                    .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                    .param(csrfToken.getParameterName(), csrfToken.getToken()))

            .andExpect(status().isCreated())
            .andExpect(jsonPath("engineType").value("cng")).andExpect((jsonPath("licensePlate").value("HR1234")));
    }


    /**
     * updateCar
     * @throws Exception
     */
    @Test
    @WithMockUser("USER")
    public void updateCar() throws Exception
    {
        LOG.info("CarControllerTest: put :updateCar");
        CarDO updateCar = new CarDO("DL1234", 2, true, 4, "cng", "bmw");
        when(carService.create(any(CarDO.class))).thenReturn(updateCar);
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put("/v1/cars/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON).content(OBJECT_MAPPER.writeValueAsString(updateCar))
                    .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                    .param(csrfToken.getParameterName(), csrfToken.getToken())

            )
            .andExpect(status().isOk());

    }


    /**
     * deleteCar
     * @throws Exception
     */
    @Test
    @WithMockUser("USER")
    public void deleteCar() throws Exception
    {
        LOG.info("CarControllerTest: delete :deleteCar");
        doNothing().when(carService).delete(1L);
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .delete("/v1/cars/1")
                    .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                    .param(csrfToken.getParameterName(), csrfToken.getToken()))
            .andExpect(status().isOk());
    }


    /**allCars
     * @throws Exception
     */
    @Test
    @WithMockUser("USER")
    public void allCars() throws Exception
    {
        LOG.info("CarControllerTest: get :allCars");
        CarDO carDO = new CarDO("DL1234", 2, true, 4, "cng", "bm");
        List<CarDO> carDOS = Arrays.asList(carDO);
        when(carService.getAllCars()).thenReturn(carDOS);
        mockMvc
            .perform(MockMvcRequestBuilders.get("/v1/cars/"))
            .andExpect(status().isOk());

    }
}
