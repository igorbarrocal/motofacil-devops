package com.motofacil.controller;

import com.motofacil.entity.Patio;
import com.motofacil.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patios")
public class PatioController {

    @Autowired
    private PatioRepository patioRepository;

    @PostMapping
    public Patio createPatio(@RequestBody Patio patio) {
        if (patioRepository.existsByCodigoUnico(patio.getCodigoUnico()))
            throw new RuntimeException("Já existe um pátio com esse código único.");
        return patioRepository.save(patio);
    }

    @GetMapping
    public List<Patio> getAllPatios() {
        return patioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Patio getPatioById(@PathVariable Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
    }

    @PutMapping("/{id}")
    public Patio updatePatio(@PathVariable Long id, @RequestBody Patio patioDetails) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));

        patio.setNome(patioDetails.getNome());
        patio.setEndereco(patioDetails.getEndereco());
        patio.setCodigoUnico(patioDetails.getCodigoUnico());
        patio.setEsp32Central(patioDetails.getEsp32Central());
        patio.setCoordenadasExtremidade(patioDetails.getCoordenadasExtremidade());

        return patioRepository.save(patio);
    }

    @DeleteMapping("/{id}")
    public void deletePatio(@PathVariable Long id) {
        if (!patioRepository.existsById(id))
            throw new RuntimeException("Pátio não encontrado");
        patioRepository.deleteById(id);
    }
}
