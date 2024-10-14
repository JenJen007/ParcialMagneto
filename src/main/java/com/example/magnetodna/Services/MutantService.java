package com.example.magnetodna.Services;

import org.springframework.stereotype.Service;

@Service
public class MutantService {
    public boolean isMutant(String[] dna) {
        int n = dna.length;

        // Si la matriz es 1x1, no es posible formar una secuencia mutante
        if (n == 1 && dna[0].length() >= 4) {
            return hasMutantSequence(dna[0]);  // Solo verifica la secuencia de la única fila
        }

        // Validación de entrada: debe ser NxN y no estar vacía
        if (n < 4 || dna[0].length() < 4) {
            throw new IllegalArgumentException("El DNA debe ser al menos de tamaño 4x4.");
        }

        for (String sequence : dna) {
            if (sequence.length() != n || !sequence.matches("[ATGC]+")) {
                throw new IllegalArgumentException("La matriz debe ser NxN y solo contener A, T, G, C.");
            }
        }

        int count = 0;

        // Verificar filas
        for (String sequence : dna) {
            if (hasMutantSequence(sequence)) {
                count++;
            }
        }

        // Verificar columnas
        for (int col = 0; col < n; col++) {
            StringBuilder columnSequence = new StringBuilder();
            for (int row = 0; row < n; row++) {
                columnSequence.append(dna[row].charAt(col));
            }
            if (hasMutantSequence(columnSequence.toString())) {
                count++;
            }
        }

        // Verificar diagonales
        for (int d = 0; d < n; d++) {
            StringBuilder diagonal1 = new StringBuilder();
            StringBuilder diagonal2 = new StringBuilder();

            // Diagonal desde la esquina superior izquierda
            for (int row = 0; row <= d; row++) {
                if (row < n && d - row < n) {
                    diagonal1.append(dna[row].charAt(d - row));
                }
            }

            // Diagonal desde la esquina inferior izquierda
            for (int row = 0; row <= d; row++) {
                if (n - 1 - row >= 0 && d - row < n) {
                    diagonal2.append(dna[n - 1 - row].charAt(d - row));
                }
            }

            if (hasMutantSequence(diagonal1.toString())) {
                count++;
            }
            if (hasMutantSequence(diagonal2.toString())) {
                count++;
            }
        }

        return count >= 2; // Al menos dos secuencias mutantes
    }

    private boolean hasMutantSequence(String sequence) {
        return sequence.contains("AAAA") || sequence.contains("TTTT") ||
                sequence.contains("CCCC") || sequence.contains("GGGG");
    }



}
