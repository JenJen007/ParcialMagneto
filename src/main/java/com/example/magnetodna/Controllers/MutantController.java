package com.example.magnetodna.Controllers;

import com.example.magnetodna.Dto.ResponseDto;
import com.example.magnetodna.Entities.DnaSequence;
import com.example.magnetodna.Repository.DnaSequenceRepository;
import com.example.magnetodna.Services.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class MutantController {
    @Autowired
    private final MutantService mutantService;
    @Autowired
    private final DnaSequenceRepository dnaSequenceRepository;
    // HashSet para almacenar resultados de secuencias procesadas

    private final Set<String> processedDna = new HashSet<>();


    public MutantController(MutantService mutantService, DnaSequenceRepository dnaSequenceRepository) {
        this.mutantService = mutantService;
        this.dnaSequenceRepository = dnaSequenceRepository;
    }

    @PostMapping("/mutant")
    public ResponseEntity<ResponseDto> isMutant(@RequestBody String[] dna) {
        // Verificar si la secuencia ya existe
        if (dnaSequenceRepository.existsByDna(dna)) {
            DnaSequence existingDnaSequence = dnaSequenceRepository.findByDna(dna);
            ResponseDto responseMessage = new ResponseDto(
                    existingDnaSequence.isMutant() ? "Mutant" : "Human",
                    existingDnaSequence.isMutant()
            );
            return ResponseEntity.ok(responseMessage);
        }

        boolean isMutant = mutantService.isMutant(dna);

        DnaSequence dnaSequence = new DnaSequence();
        dnaSequence.setDna(dna);
        dnaSequence.setMutant(isMutant);
        dnaSequenceRepository.save(dnaSequence);

        ResponseDto responseMessage = new ResponseDto(
                isMutant ? "Mutant" : "Human",
                isMutant
        );

        return ResponseEntity.ok(responseMessage);
    }


}
