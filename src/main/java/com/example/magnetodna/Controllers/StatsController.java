package com.example.magnetodna.Controllers;

import com.example.magnetodna.Repository.DnaSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatsController {
    @Autowired
    private final DnaSequenceRepository dnaSequenceRepository;

    public StatsController(DnaSequenceRepository dnaSequenceRepository) {
        this.dnaSequenceRepository = dnaSequenceRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long countMutantDna = dnaSequenceRepository.countByIsMutant(true);
        long countHumanDna = dnaSequenceRepository.countByIsMutant(false);
        double ratio = (countHumanDna == 0) ? 0 : (double) countMutantDna / countHumanDna;

        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", countMutantDna);
        stats.put("count_human_dna", countHumanDna);
        stats.put("ratio", ratio);

        return ResponseEntity.ok(stats);
    }
}