package com.udacity.pricing.api;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PricingService priceService;


    @Before
    public void setup() throws PriceException {
        Price price = getMockPrice();
        given(priceService.createPrice(any())).willReturn(price);
        given(priceService.getPrice(any())).willReturn(price);
    }

    @Test
    public void getPrice() throws Exception {
        mvc.perform(get("/services/price/")
                    .param("vehicleId", "12"))
                .andExpect(status().isOk());
        verify(priceService, times(1)).getPrice(12L);
    }

    @Test
    public void createPrice() throws Exception {
        mvc.perform(post("/services/price/")
                        .param("vehicleId", "12"))
                .andExpect(status().isCreated());
        verify(priceService, times(1)).createPrice(12L);
    }

    @Test
    public void deletePrice() throws Exception {
        mvc.perform(delete("/services/price/")
                        .param("vehicleId", "12"))
                .andExpect(status().isNoContent());
        verify(priceService, times(1)).deletePrice(12L);
    }


    private Price getMockPrice() {
        return new Price(1L, "USD", BigDecimal.valueOf(5500), 12L);
    }
}
