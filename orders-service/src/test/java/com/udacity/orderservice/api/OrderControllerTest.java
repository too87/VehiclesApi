package com.udacity.orderservice.api;

import com.udacity.orderservice.client.*;
import com.udacity.orderservice.domain.OrderRepresentation;
import com.udacity.orderservice.entity.Order;
import com.udacity.orderservice.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @Before
    public void setup() {
        given(orderService.createOrder(any(Condition.class), any(Manufacturer.Brands.class), anyInt()))
                .willReturn(new OrderRepresentation(getCar(), 1L));
        given(orderService.get(any())).willReturn(new OrderRepresentation(getCar(), 1L));
        given(orderService.list()).willReturn(Collections.singletonList(new OrderRepresentation(getCar(), 1L)));
    }

    @Test
    public void testList() throws Exception {
        mvc.perform(get("/orders"))
                .andExpect(status().isOk());
        verify(orderService, times(1)).list();
    }

    @Test
    public void testCreateOrder() throws Exception {
        mvc.perform(post("/orders")
                        .param("condition", "USED")
                        .param("manufacturer", Manufacturer.Brands.BMW.name())
                        .param("modelYear", "2020"))
                .andExpect(status().isCreated());
        verify(orderService, times(1)).createOrder(Condition.USED, Manufacturer.Brands.BMW, 2020);
    }

    @Test
    public void testGetOrder() throws Exception {
        mvc.perform(get("/orders/1"))
                .andExpect(status().isOk());
        verify(orderService, times(1)).get(1L);
    }

    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}
