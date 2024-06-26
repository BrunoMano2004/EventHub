package com.eventHub.controller;

import com.eventHub.dto.endereco.AtualizacaoEnderecoDto;
import com.eventHub.dto.endereco.CadastroEnderecoDto;
import com.eventHub.dto.endereco.ListagemEnderecoDto;
import com.eventHub.dto.login.CadastroLoginDto;
import com.eventHub.dto.usuario.AtualizacaoUsuarioDto;
import com.eventHub.dto.usuario.CadastroUsuarioDto;
import com.eventHub.dto.usuario.ListagemUsuarioDto;
import com.eventHub.model.Usuario;
import com.eventHub.service.LoginService;
import com.eventHub.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private LoginService loginService;

    @Autowired
    private MockMvc mvc;

    @Test
    void deveriaRetornarPaginaCadastroUsuario() throws Exception {
        mvc.perform(
                get("/usuarios/cadastro")
        ).andExpect(status().isOk())
                .andExpect(view().name("cadastroUsuario"));
    }

    @Test
    void deveriaCadastrarUsuarioERetornarPaginaValidacaoEmailComDadosValidos() throws Exception {
        mvc.perform(
                post("/usuarios/cadastro")
                        .param("nome", "nome")
                        .param("identidade", "99999999999")
                        .param("tipoDocumento", "CPF")
                        .param("email", "email@emai.com")
                        .param("telefone", "11999999999")
                        .param("senha", "senha")
                        .param("role", "USER")
                        .param("logradouro", "logradouro")
                        .param("numero", "99")
                        .param("bairro", "bairro")
                        .param("cidade", "cidade")
                        .param("uf", "uf")
                        .param("cep", "99999999")
                        .param("complemento", "complemento")
        ).andExpect(status().isOk())
                .andExpect(view().name("avisoValidacaoEmail"));

        verify(usuarioService).cadastrarUsuario(any(CadastroUsuarioDto.class), any(CadastroLoginDto.class), any(CadastroEnderecoDto.class));
    }

    @Test
    void deveriaRetornarExcecaoDeValidacaoDeDadosComDadosErrados() throws Exception {
        mvc.perform(
                        post("/usuarios/cadastro")
                                .param("nome", "")
                                .param("identidade", "")
                                .param("tipoDocumento", "")
                                .param("email", "")
                                .param("telefone", "")
                                .param("senha", "")
                                .param("role", "")
                                .param("logradouro", "")
                                .param("numero", "")
                                .param("bairro", "")
                                .param("cidade", "")
                                .param("uf", "")
                                .param("cep", "")
                                .param("complemento", "")
                ).andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user")
    void deveriaRetornarPaginaDashboardComDadosDoUsuarioComUsuarioAutenticado() throws Exception {
        CadastroUsuarioDto usuarioDto = new CadastroUsuarioDto("user", "99999999999", "CPF", "email@email.com", "11999999999");
        CadastroEnderecoDto enderecoDto = new CadastroEnderecoDto("logradouro", 99, "bairro", "cidade", "uf", "99999999", "complemento");
        Usuario usuario = new Usuario(usuarioDto, enderecoDto);
        ListagemUsuarioDto listagemUsuarioDto = new ListagemUsuarioDto(usuario);
        ListagemEnderecoDto listagemEnderecoDto = new ListagemEnderecoDto(usuario.getEndereco());

        when(loginService.retornarUsuarioDaSessao()).thenReturn(usuario);

        mvc.perform(
                get("/usuarios/dashboard")
        ).andExpect(status().isOk())
                .andExpect(model().attribute("usuario", listagemUsuarioDto))
                .andExpect(model().attribute("endereco", listagemEnderecoDto))
                .andExpect(view().name("dashboard"));
    }

    @Test
    void deveriaRedirecionarPaginaLoginComUsuarioNaoAutenticado() throws Exception {
        mvc.perform(
                        get("/usuarios/dashboard")
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "user")
    void deveriaRedirecionarParaPaginaDashboardComEmailSemAlteracaoEComDadosValidos() throws Exception {
        when(usuarioService.atualizarDadosUsuario(any(AtualizacaoUsuarioDto.class), any(AtualizacaoEnderecoDto.class))).thenReturn(true);

        mvc.perform(
                post("/usuarios/dashboard")
                        .param("id", "10")
                        .param("nome", "nome")
                        .param("email", "email@email.com")
                        .param("telefone", "11999999999")
                        .param("logradouro", "logradouro")
                        .param("numero", "99")
                        .param("bairro", "bairro")
                        .param("cidade", "cidade")
                        .param("uf", "uf")
                        .param("cep", "99999999")
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios/dashboard"));
    }

    @Test
    @WithMockUser(username = "user")
    void deveriaRetornarPaginaDeVerificacaoDeEmailComEmailComAlteracaoEComDadosValidos() throws Exception {
        when(usuarioService.atualizarDadosUsuario(any(AtualizacaoUsuarioDto.class), any(AtualizacaoEnderecoDto.class))).thenReturn(false);

        mvc.perform(
                        post("/usuarios/dashboard")
                                .param("id", "10")
                                .param("nome", "nome")
                                .param("email", "email@email.com")
                                .param("telefone", "11999999999")
                                .param("logradouro", "logradouro")
                                .param("numero", "99")
                                .param("bairro", "bairro")
                                .param("cidade", "cidade")
                                .param("uf", "uf")
                                .param("cep", "99999999")
                ).andExpect(status().isOk())
                .andExpect(view().name("avisoValidacaoEmail"));
    }

    @Test
    @WithMockUser(username = "user")
    void deveriaRedirecionarPaginaDeLoginComDadosInvalidos() throws Exception {
        mvc.perform(
                        post("/usuarios/dashboard")
                                .param("id", "")
                                .param("nome", "")
                                .param("email", "")
                                .param("telefone", "")
                                .param("logradouro", "")
                                .param("numero", "")
                                .param("bairro", "")
                                .param("cidade", "")
                                .param("uf", "")
                                .param("cep", "")
                ).andExpect(status().is4xxClientError());
    }

    @Test
    void deveriaRedirecionarPaginaDeLoginSemUsuarioLogado() throws Exception {
        mvc.perform(
                        post("/usuarios/dashboard")
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}