package com.motofacil.repository;

import com.motofacil.entity.Moto;
import com.motofacil.entity.Patio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    List<Moto> findByPatio(Patio patio);
}
