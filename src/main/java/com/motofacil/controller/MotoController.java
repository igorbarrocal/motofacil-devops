package com.motofacil.controller;

import com.motofacil.dto.LocationDTO;
import com.motofacil.entity.Location;
import com.motofacil.entity.Moto;
import com.motofacil.entity.Patio;
import com.motofacil.repository.LocationRepository;
import com.motofacil.repository.MotoRepository;
import com.motofacil.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PatioRepository patioRepository;

    // Criar nova moto vinculada a um pátio
    @PostMapping
    public Moto createMoto(@RequestBody Moto moto) {
        if (moto.getPatio() == null || moto.getPatio().getId() == null)
            throw new RuntimeException("Moto precisa ser vinculada a um pátio válido.");

        Patio patio = patioRepository.findById(moto.getPatio().getId())
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado."));
        moto.setPatio(patio);
        moto.setStatus("pendente"); // status inicial

        return motoRepository.save(moto);
    }

    // Atualizar localização de uma moto (recebe dados reais do ESP)
    @PutMapping("/{id}/location")
    public Moto updateMotoLocation(@PathVariable Long id, @RequestBody LocationDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        Patio patio = patioRepository.findById(dto.getPatioId())
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));

        Location location = new Location();
        location.setX(dto.getX());
        location.setY(dto.getY());
        location.setTimestamp(LocalDateTime.now());
        location.setMoto(moto);
        location.setPatio(patio);
        location.setTag(dto.getTag() != null ? dto.getTag() : "patio");

        locationRepository.save(location);

        moto.setLocation(location);
        moto.setStatus(dto.getTag() != null ? dto.getTag() : "patio");
        return motoRepository.save(moto);
    }

    // Última localização de uma moto
    @GetMapping("/{id}/location")
    public Location getMotoLocation(@PathVariable Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        return moto.getLocation();
    }

    // Histórico completo de uma moto
    @GetMapping("/{id}/history")
    public List<Location> getMotoHistory(@PathVariable Long id) {
        return locationRepository.findByMotoIdOrderByTimestampDesc(id);
    }

    // Listar todas as motos
    @GetMapping
    public List<Moto> getAllMotos() {
        return motoRepository.findAll();
    }

    // Buscar moto por ID
    @GetMapping("/{id}")
    public Moto getMotoById(@PathVariable Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
    }

    // Listar motos por pátio
    @GetMapping("/patio/{patioId}")
    public List<Moto> getMotosByPatio(@PathVariable Long patioId) {
        Patio patio = patioRepository.findById(patioId)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
        return motoRepository.findByPatio(patio);
    }
}
