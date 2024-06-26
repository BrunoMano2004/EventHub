package com.eventHub.service;

import com.eventHub.exception.ValidacaoEmailException;
import com.eventHub.model.Login;
import com.eventHub.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoService {

    @Autowired
    private JwtTokeUtil jwtTokeUtil;

    @Autowired
    private LoginRepository loginRepository;

    public void validarEmail(String jwt) {
        try {
            String email = jwtTokeUtil.extractUsername(jwt);
            Login login = loginRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Login não encontrado para o email!"));
            if (jwtTokeUtil.validateToken(jwt)){
                login.setAtivo(true);
            } else {
                throw new ValidacaoEmailException("Token inválido ou vencido!");
            }
        } catch (Exception e){
            throw new ValidacaoEmailException("Token inválido ou vencido!");
        }
    }
}
