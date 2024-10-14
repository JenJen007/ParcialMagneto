package com.example.magnetodna.Controllers;

import com.example.magnetodna.Repository.DnaSequenceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatsController {
    private final DnaSequenceRepository dnaSequenceRepository;

    public StatsController(DnaSequenceRepository dnaSequenceRepository) {
        this.dnaSequenceRepository = dnaSequenceRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        long countMutants = dnaSequenceRepository.countByIsMutant(true);
        long countHumans = dnaSequenceRepository.countByIsMutant(false);
        double ratio = countHumans == 0 ? 0 : (double) countMutants / countHumans;

        // Crear un Map para devolver como respuesta JSON
        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", countMutants);
        stats.put("count_human_dna", countHumans);
        stats.put("ratio", ratio);

        return stats;
    }
}