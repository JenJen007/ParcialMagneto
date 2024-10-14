package com.example.magnetodna.Repository;

import com.example.magnetodna.Entities.DnaSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnaSequenceRepository extends JpaRepository<DnaSequence,Long> {
    boolean existsByDna(String[] dna);

    // Métodos para contar humanos y mutantes
    long countByIsMutant(boolean isMutant);

    DnaSequence findByDna(String[] dna);
}
