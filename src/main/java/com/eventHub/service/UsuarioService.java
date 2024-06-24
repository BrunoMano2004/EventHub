package com.eventHub.service;

import com.eventHub.dto.endereco.AtualizacaoEnderecoDto;
import com.eventHub.dto.endereco.CadastroEnderecoDto;
import com.eventHub.dto.login.CadastroLoginDto;
import com.eventHub.dto.usuario.AtualizacaoUsuarioDto;
import com.eventHub.dto.usuario.CadastroUsuarioDto;
import com.eventHub.exception.ValidacaoEmailException;
import com.eventHub.model.Login;
import com.eventHub.model.Role;
import com.eventHub.model.Usuario;
import com.eventHub.repository.EnderecoRepository;
import com.eventHub.repository.LoginRepository;
import com.eventHub.repository.RoleRepository;
import com.eventHub.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokeUtil jwtTokeUtil;

    private final String urlVerificacaoEmail = "http://localhost:8080/validar/";

    public void cadastrarUsuario(CadastroUsuarioDto usuarioDto, CadastroLoginDto loginDto, CadastroEnderecoDto enderecoDto) {
        Usuario usuario = new Usuario(usuarioDto, enderecoDto);
        usuarioRepository.save(usuario);

        Login login = new Login();
        login.setEmail(usuarioDto.email());
        login.setSenha(passwordEncoder.encode(loginDto.senha()));
        System.out.println(passwordEncoder.encode(loginDto.senha()));
        login.setUsuario(usuario);
        login.setAtivo(false);

        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.findByName(loginDto.role()).orElseThrow(() -> new RuntimeException("Role não encontrada!"));

        roles.add(role);
        login.setRoles(roles);
        loginRepository.save(login);

        sendJwtEmail(login.getEmail(), urlVerificacaoEmail);
    }

    public boolean atualizarDadosUsuario(AtualizacaoUsuarioDto usuarioDto, AtualizacaoEnderecoDto enderecoDto) {
        Usuario usuario = usuarioRepository.getReferenceById(usuarioDto.id());
        Login login = loginRepository.findByEmail(usuario.getEmail()).get();
        login.setEmail(usuarioDto.email());
        if(!Objects.equals(usuario.getEmail(), usuarioDto.email())){
            sendJwtEmail(usuarioDto.email(), urlVerificacaoEmail);
            login.setAtivo(false);
            usuario.atualizarUsuario(usuarioDto, enderecoDto);
            return false;
        }
        usuario.atualizarUsuario(usuarioDto, enderecoDto);
        return true;
    }

    public void sendJwtEmail(String email, String url){
        loginRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email não econtrado!"));
        Context context = new Context();
        String jwtToken = jwtTokeUtil.generateToken(email);
        context.setVariable("jwt", url + jwtToken);
        System.out.println(jwtToken);

        try {
            emailService.sendHtmlEmail(email, "Seu link de validação", "templateEmail", context);
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
