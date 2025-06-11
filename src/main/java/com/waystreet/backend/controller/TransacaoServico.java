package com.waystreet.backend.controller;

import com.waystreet.backend.model.Transacao;
import com.waystreet.backend.repositorio.TransacaoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth; 
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoServico {

    @Autowired 
    private TransacaoRepositorio transacaoRepositorio; 

    /**
     * Cadastra uma nova transação financeira.
     * Inclui validações básicas para valor e tipo.
     * @param transacao Objeto Transacao a ser salvo.
     * @return A transação salva com o ID gerado.
     * @throws IllegalArgumentException
     */
    public Transacao cadastrarTransacao(Transacao transacao) {
        if (transacao.getValor() == null || transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        if (transacao.getTipo() == null || (!transacao.getTipo().equalsIgnoreCase("entrada") && !transacao.getTipo().equalsIgnoreCase("saida"))) {
            throw new IllegalArgumentException("O tipo da transação deve ser 'entrada' ou 'saida'.");
        }
        if (transacao.getData() == null) {
            transacao.setData(LocalDate.now());
        }
        return transacaoRepositorio.save(transacao);
    }

    /**
     * Lista todas as transações financeiras cadastradas.
     * @return Uma lista de todas as transações.
     */
    public List<Transacao> listarTodasTransacoes() {
        return transacaoRepositorio.findAll();
    }

    /**
     * Busca uma transação específica pelo seu ID.
     * @param id O ID da transação.
     * @return Um Optional contendo a transação se encontrada, ou um Optional vazio.
     */
    public Optional<Transacao> buscarTransacaoPorId(Long id) {
        return transacaoRepositorio.findById(id);
    }

    /**
     * Lista transações filtradas por mês e ano.
     * @param ano
     * @param mes
     * @return
     */
    public List<Transacao> listarTransacoesPorMesAno(int ano, int mes) {
        YearMonth yearMonth = YearMonth.of(ano, mes);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return transacaoRepositorio.findByDataBetween(startDate, endDate);
    }

    /**
     * Lista transações filtradas por um período de datas.
     * @param dataInicio
     * @param dataFim
     * @return
     */
    public List<Transacao> buscarTransacoesPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return transacaoRepositorio.findByDataBetween(dataInicio, dataFim);
    }

    /**
     * Calcula o saldo total do fluxo de caixa.
     * @return
     */
    public BigDecimal calcularSaldoTotal() {
        List<Transacao> transacoes = transacaoRepositorio.findAll();
        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : transacoes) {
            if ("entrada".equalsIgnoreCase(transacao.getTipo())) {
                saldo = saldo.add(transacao.getValor());
            } else if ("saida".equalsIgnoreCase(transacao.getTipo())) {
                saldo = saldo.subtract(transacao.getValor());
            }
        }
        return saldo;
    }

    /**
     * Exclui uma transação pelo seu ID.
     * @param id O ID da transação a ser excluída.
     */
    public void deletarTransacao(Long id) {
        transacaoRepositorio.deleteById(id);
    }
}