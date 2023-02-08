package com.tales.miniautorizador.repository;

import com.tales.miniautorizador.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao,Long> {
    void deleteByNumCartao(Long numCartao);
}
