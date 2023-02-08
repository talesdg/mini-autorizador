package com.tales.miniautorizador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@Entity
@NoArgsConstructor
@Table(name = "cartao")
public class Cartao {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "numCartao", nullable = false)
    private Long numCartao;
    @Column(name = "senha", nullable = false)
    private String senha;
    @JsonIgnore
    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    public Cartao(Long numCartao, String senha){
        this.numCartao = numCartao;
        this.senha = senha;
        this.saldo = new BigDecimal("500.00");
    }

    public void performDebit(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }
}
