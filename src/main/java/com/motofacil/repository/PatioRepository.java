package com.motofacil.repository;

import com.motofacil.entity.Patio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatioRepository extends JpaRepository<Patio, Long> {
    boolean existsByNomeAndEndereco(String nome, String endereco);
    boolean existsByCodigoUnico(String codigoUnico);
}
