package com.tales.miniautorizador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Getter
@Entity
@NoArgsConstructor
@Table(name = "cartao")
public class Cartao {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "numeroCartao", nullable = false)
    private Long numeroCartao;
    @Column(name = "senha", nullable = false)
    private String senha;
    @JsonIgnore
    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    public Cartao(Long numeroCartao, String senha){
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = new BigDecimal("500.00");
    }

    public void performDebit(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }
}
