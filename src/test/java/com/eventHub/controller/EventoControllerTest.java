package com.eventHub.controller;

import com.eventHub.dto.evento.ListagemEventosDto;
import com.eventHub.service.EventoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventoService eventoService;

    private List<ListagemEventosDto> eventos = new ArrayList<>();

    @Test
    void deveriaListarTodosEventosDisponiveis() throws Exception {
        when(eventoService.listarEventosDisponiveis()).thenReturn(eventos);

        mvc.perform(
                get("/eventos")
        ).andExpect(view().name("index"))
                .andExpect(model().attributeExists("eventos"))
                .andExpect(model().attribute("eventos", eventos));
    }

    @Test
    void deveriaListarTodosEventosComNomeParecido() throws Exception {
        when(eventoService.listarEventosPeloNome(anyString())).thenReturn(eventos);

        mvc.perform(
                        get("/eventos/pesquisar")
                ).andExpect(view().name("index"))
                .andExpect(model().attributeExists("eventos"))
                .andExpect(model().attribute("eventos", eventos));
    }

    @Test
    @WithMockUser(username = "organizador", roles = {"ORGANIZER"})
    void deveriaRetornarPaginaDeCadastro() throws Exception {
        mvc.perform(
                get("/eventos/cadastrar")
        ).andExpect(view().name("cadastroEvento"));
    }

    @Test
    @WithMockUser(username = "user")
    void deveriaRetornar403ParaUsuarioComum() throws Exception {
        mvc.perform(
                get("/eventos/cadastrar")
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "organizador", roles = {"ORGANIZER"})
    void deveriaCadastrarEventoSemErro() throws Exception {
        mvc.perform(
                post("/eventos/cadastrar")
                        .with(csrf())
                        .param("nome", "evento")
                        .param("descricao", "evento")
                        .param("dataInicio", "20-10-2024")
                        .param("dataEncerramento", "21-10-2024")
                        .param("horarioEncerramento", "10:00")
                        .param("horarioInicio", "12:00")
                        .param("local", "local")
                        .param("capacidade", "1000")
                        .param("preco", "10.00")
                        .param("imagem", "imagem")
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/eventos"));
    }

    @Test
    @WithMockUser(username = "user")
    void deveriaRetornar403ParaUsuarioNaoOrganizador() throws Exception {
        mvc.perform(
                post("/eventos/cadastrar")
                        .param("nome", "evento")
                        .param("descricao", "evento")
                        .param("dataInicio", "20-10-2024")
                        .param("dataEncerramento", "21-10-2024")
                        .param("horarioEncerramento", "10:00")
                        .param("horarioInicio", "12:00")
                        .param("local", "local")
                        .param("capacidade", "1000")
                        .param("preco", "10.00")
                        .param("imagem", "imagem")
        ).andExpect(status().isForbidden());
    }

    @Test
    void deveriaRetornar403ParaUsuarioNaoLogado() throws Exception {
        mvc.perform(
                post("/eventos/cadastrar")
                        .param("nome", "evento")
                        .param("descricao", "evento")
                        .param("dataInicio", "20-10-2024")
                        .param("dataEncerramento", "21-10-2024")
                        .param("horarioEncerramento", "10:00")
                        .param("horarioInicio", "12:00")
                        .param("local", "local")
                        .param("capacidade", "1000")
                        .param("preco", "10.00")
                        .param("imagem", "imagem")
        ).andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }
}