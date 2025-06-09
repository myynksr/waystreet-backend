package com.example.fluxocaixa.model;

import jakarta.persistence.*; // Importa anotações JPA para mapeamento objeto-relacional
import lombok.Data; // Importa a anotação @Data do Lombok para gerar getters, setters, etc.
import lombok.NoArgsConstructor; // Importa a anotação @NoArgsConstructor do Lombok
import lombok.AllArgsConstructor; // Importa a anotação @AllArgsConstructor do Lombok

import java.math.BigDecimal; // Usado para valores monetários para evitar imprecisões de ponto flutuante
import java.time.LocalDate; // Usado para armazenar a data da transação

@Entity // Marca esta classe como uma entidade JPA, mapeando-a para uma tabela no banco de dados
@Data // Anotação Lombok: Gera automaticamente getters, setters, toString(), equals() e hashCode()
@NoArgsConstructor // Anotação Lombok: Gera um construtor sem argumentos
@AllArgsConstructor // Anotação Lombok: Gera um construtor com todos os argumentos
public class Transacao {

    @Id // Marca o campo 'id' como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática do ID (auto-incremento no PostgreSQL)
    private Long id; // Identificador único da transação

    private String descricao; // Descrição da transação (ex: "Salário", "Aluguel")
    private BigDecimal valor; // Valor da transação (usar BigDecimal para precisão monetária)

    @Enumerated(EnumType.STRING) // Mapeia o enum para uma string no banco de dados (ex: "RECEITA", "DESPESA")
    private TipoTransacao tipo; // Tipo da transação (Receita ou Despesa)

    private LocalDate data; // Data em que a transação ocorreu
}