package com.udacity.boogle.maps.api;

import com.udacity.boogle.maps.service.AddressService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MapsController.class)
public class MapsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService addressService;

    @Test
    public void getAddress() throws Exception {
        mvc.perform(get("/maps")
                    .param("lat", "12.0")
                    .param("lon", "30.0"))
                .andExpect(status().isOk());
        verify(addressService, times(1)).createRandomAddress(12.0, 30.0);
    }
}
