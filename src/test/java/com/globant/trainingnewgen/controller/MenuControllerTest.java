package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.model.factory.MenuFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuFactory menuFactory;

    @Test
    @DisplayName("Should return 400 Bad Request for unsupported content type")
    void shouldReturnBadRequestForUnsupportedContentType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu")
                        .header("Content-Type", "unsupported/type"))
                .andExpect(status().isBadRequest());
    }

}
