package com.waystreet.backend.servico;

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

    public List<Transacao> listarTodasTransacoes() {
        return transacaoRepositorio.findAll();
    }

    public Optional<Transacao> buscarTransacaoPorId(Long id) {
        return transacaoRepositorio.findById(id);
    }

    public List<Transacao> listarTransacoesPorMesAno(int ano, int mes) {
        YearMonth yearMonth = YearMonth.of(ano, mes);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return transacaoRepositorio.findByDataBetween(startDate, endDate);
    }

    public List<Transacao> buscarTransacoesPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return transacaoRepositorio.findByDataBetween(dataInicio, dataFim);
    }

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

    public void deletarTransacao(Long id) {
        transacaoRepositorio.deleteById(id);
    }
}
