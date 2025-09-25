package com.motofacil.controller;

import com.motofacil.dto.LocationDTO;
import com.motofacil.entity.Location;
import com.motofacil.entity.Moto;
import com.motofacil.entity.Patio;
import com.motofacil.repository.LocationRepository;
import com.motofacil.repository.MotoRepository;
import com.motofacil.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PatioRepository patioRepository;

    // Salvar nova localização
    @PostMapping
    public ResponseEntity<Location> saveLocation(@RequestBody LocationDTO dto) {
        try {
            Moto moto = motoRepository.findById(dto.getMotoId())
                    .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

            if (moto.getPatio() == null)
                return ResponseEntity.badRequest()
                        .body(null); // Moto não vinculada a um pátio

            Location location = new Location();
            location.setX(dto.getX());
            location.setY(dto.getY());
            location.setTimestamp(LocalDateTime.now());
            location.setMoto(moto);
            location.setPatio(moto.getPatio());
            location.setTag(dto.getTag() != null ? dto.getTag() : "patio");

            Location saved = locationRepository.save(location);
            moto.setLocation(saved);
            motoRepository.save(moto);

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    // Buscar última localização de uma moto
    @GetMapping("/moto/{id}/latest")
    public ResponseEntity<Location> getLatestByMoto(@PathVariable Long id) {
        return locationRepository.findTopByMotoIdOrderByTimestampDesc(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar histórico completo de uma moto
    @GetMapping("/moto/{id}/history")
    public ResponseEntity<List<Location>> getHistoryByMoto(@PathVariable Long id) {
        try {
            List<Location> history = locationRepository.findByMotoIdOrderByTimestampDesc(id);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}
