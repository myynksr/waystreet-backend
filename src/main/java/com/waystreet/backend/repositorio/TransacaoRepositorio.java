// Exemplo de TransacaoRepositorio.java
package com.waystreet.backend.repositorio;

import com.waystreet.backend.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransacaoRepositorio extends JpaRepository<Transacao, Long> {
    // Método para encontrar transações dentro de um período de datas
    List<Transacao> findByDataBetween(LocalDate startDate, LocalDate endDate);

}