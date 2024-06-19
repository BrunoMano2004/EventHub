package com.eventHub.validacao;

import com.eventHub.dto.evento.CadastroEventoDto;
import com.eventHub.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Component
public class ValidacaoDatasInicioEEncerramento implements ValidacoesCadastroEventos{

    @Override
    public void validar(CadastroEventoDto dto) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate dataInicio = LocalDate.parse(sdf.format(dto.dataInicio()));
        LocalDate dataEncerramento = LocalDate.parse(sdf.format(dto.dataEncerramento()));

        if (dataInicio.isBefore(dataEncerramento)){
            throw new ValidacaoException("Data de início não deve ser depois da data de encerramento!");
        }
    }
}
