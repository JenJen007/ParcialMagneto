package com.example.magnetodna.Controllers;

import com.example.magnetodna.Dto.ResponseDto;
import com.example.magnetodna.Entities.DnaSequence;
import com.example.magnetodna.Repository.DnaSequenceRepository;
import com.example.magnetodna.Services.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            // Verificar si la secuencia ya existe
            if (dnaSequenceRepository.existsByDna(dna)) {
                DnaSequence existingDnaSequence = dnaSequenceRepository.findByDna(dna);
                ResponseDto responseMessage = new ResponseDto(
                        existingDnaSequence.isMutant() ? "Mutant" : "Human",
                        existingDnaSequence.isMutant()
                );
                // Si es mutante, retornar 200, de lo contrario, 403
                return existingDnaSequence.isMutant() ?
                        ResponseEntity.ok(responseMessage) :
                        ResponseEntity.status(403).body(responseMessage);
            }

            // Validar la secuencia de ADN (puedes agregar validaciones aquí)
            if (dna == null || dna.length == 0) {
                throw new IllegalArgumentException("DNA sequence cannot be null or empty");
            }

            // Procesar la secuencia para determinar si es mutante
            boolean isMutant = mutantService.isMutant(dna);

            // Guardar el resultado en la base de datos
            DnaSequence dnaSequence = new DnaSequence();
            dnaSequence.setDna(dna);
            dnaSequence.setMutant(isMutant);
            dnaSequenceRepository.save(dnaSequence);

            ResponseDto responseMessage = new ResponseDto(
                    isMutant ? "Mutant" : "Human",
                    isMutant
            );

            // Si es mutante, retornar 200, de lo contrario, retornar 403
            return isMutant ?
                    ResponseEntity.ok(responseMessage) :
                    ResponseEntity.status(403).body(responseMessage);

        } catch (IllegalArgumentException e) {
            // Retornar 400 Bad Request en caso de que se lance una excepción
            return ResponseEntity.badRequest().body(new ResponseDto(e.getMessage(), false));
        }
    }
}
