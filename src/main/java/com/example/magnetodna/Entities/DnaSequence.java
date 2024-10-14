package com.example.magnetodna.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;


import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dna_sequences")
@Audited

public class DnaSequence implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 500)
    private String[] dna;

    @Column
    private boolean isMutant;

    public DnaSequence(String[] dna, boolean isMutant) {
        this.dna = dna;
        this.isMutant = isMutant;
    }

}

