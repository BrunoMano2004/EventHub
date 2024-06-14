package com.eventHub.repository;

import com.eventHub.model.Evento;
import com.eventHub.model.StatusEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findAllByStatus(StatusEvento status);

    @Query("select e from Evento e where e.nome like %:nomeEvento%")
    List<Evento> encontrarEventosPeloNome(String nomeEvento);
}
