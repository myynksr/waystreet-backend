package com.waystreet.backend.controller;

import com.waystreet.backend.model.Transacao;
import com.waystreet.backend.servico.TransacaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController {

    @Autowired
    private TransacaoServico servico;

    @PostMapping
    public Transacao criarTransacao(@RequestBody Transacao transacao) {
        return servico.cadastrarTransacao(transacao);
    }

    @GetMapping
    public List<Transacao> listarTodas() {
        return servico.listarTodasTransacoes();
    }

    @GetMapping("/{id}")
    public Optional<Transacao> buscarPorId(@PathVariable Long id) {
        return servico.buscarTransacaoPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        servico.deletarTransacao(id);
    }

    @GetMapping("/saldo")
    public BigDecimal saldoTotal() {
        return servico.calcularSaldoTotal();
    }
}
