package com.eventHub.controller;

import com.eventHub.exception.ValidacaoEmailException;
import com.eventHub.service.LoginService;
import com.eventHub.service.UsuarioService;
import com.eventHub.service.ValidacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoginService loginService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private ValidacaoService validacaoService;

    @Test
    void deveriaRetornarPaginaLogin() throws Exception {
        mvc.perform(
                get("/login")
        ).andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void deveriaRetornarPaginaRecuperarSenha() throws Exception {
        mvc.perform(
                        get("/recuperarSenha")
                ).andExpect(status().isOk())
                .andExpect(view().name("recuperarSenha"));
    }

    @Test
    void deveriaRetornarPaginaAvisoValidacaoEmail() throws Exception {
        mvc.perform(
                        post("/recuperarSenha")
                                .param("email", "email@email.com")
                ).andExpect(status().isOk())
                .andExpect(view().name("avisoValidacaoEmail"));
    }

    @Test
    void deveriaRetornarPaginaRedefinicaoSenhaComJwtValido() throws Exception {
        String jwt = "12345";

        mvc.perform(
                get("/redefinirSenha/{jwt}", jwt)
        ).andExpect(status().isOk())
                .andExpect(view().name("redefinicaoSenha"));
    }

    @Test
    void deveriaRetornarPaginaLoginComJwtInvalido() throws Exception {
        String jwt = "12345";

        doThrow(new ValidacaoEmailException("")).when(validacaoService).validarEmail(jwt);

        mvc.perform(
                        get("/redefinirSenha/{jwt}", jwt)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void deveriaAlterarSenhaERetornarPaginaLogin() throws Exception {
        String jwt = "12345";
        String senha = "senha";

        mvc.perform(
                post("/redefinirSenha/{jwt}", jwt)
                        .param("senha", senha)
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        then(loginService).should().redefinirSenha(jwt, senha);
    }
}