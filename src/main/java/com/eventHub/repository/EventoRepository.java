package com.eventHub.repository;

import com.eventHub.model.Evento;
import com.eventHub.model.StatusEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findAllByStatus(StatusEvento status);

    @Query("select e from Evento e where e.nome like %:nomeEvento%")
    List<Evento> encontrarEventosPeloNome(String nomeEvento);

    @Query("select e from Evento e where dataInicio <= :dataAtual and horarioEncerramento <= :horarioAtual")
    Optional<List<Evento>> encontrarEventosJaConcluidos(LocalTime horarioAtual, LocalDate dataAtual);
}
