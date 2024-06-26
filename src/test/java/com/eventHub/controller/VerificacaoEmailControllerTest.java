package com.eventHub.controller;

import com.eventHub.exception.ValidacaoEmailException;
import com.eventHub.service.ValidacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VerificacaoEmailController.class)
@AutoConfigureMockMvc(addFilters = false)
class VerificacaoEmailControllerTest {

    @MockBean
    private ValidacaoService validacaoService;

    @Autowired
    private MockMvc mvc;

    @Test
    void deveriaRedirecionarPagindaLogin() throws Exception {
        String jwt = "12345";

        mvc.perform(
                get("/validar/{jwt}", jwt)
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void deveriaRetornarPaginaFalhaVerificacaoEmailComTokenInvalido() throws Exception {
        String jwt = "12345";

        doThrow(new ValidacaoEmailException("")).when(validacaoService).validarEmail(jwt);

        mvc.perform(
                        get("/validar/{jwt}", jwt)
                ).andExpect(status().isOk())
                .andExpect(view().name("falhaVerificacaoEmail"));
    }
}