package com.example.magnetodna;

import com.example.magnetodna.Controllers.StatsController;
import com.example.magnetodna.Entities.DnaSequence;
import com.example.magnetodna.Repository.DnaSequenceRepository;
import com.example.magnetodna.Services.MutantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MagnetodnaApplicationTests {
	@Autowired
	private MutantService mutantService;
	@Autowired
	private DnaSequenceRepository dnaSequenceRepository;

	@Autowired
	private StatsController statsController;

	@ParameterizedTest
	@CsvSource({
			"'ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG', true",
			"'ATGCGA,CAGTGC,TTATTT,AGACGG,GCGTCA,TCACTG', false",
			"'AAAA,AAAA,AAAA,AAAA', true",  // Caso mutante con secuencias múltiples
			"'AAAA', true"  // Caso de una fila mutante simple
	})
	void testIsMutant(String dnaString, boolean expected) {
		String[] dna = dnaString.split(","); // convierte la cadena a un arreglo
		assertEquals(expected, mutantService.isMutant(dna));
	}

	@Test
	void testEmptyDna() {
		String[] emptyDna = {};
		assertThrows(IllegalArgumentException.class, () -> mutantService.isMutant(emptyDna));
	}

	@Test
	void testInvalidDnaCharacters() {
		String[] invalidDna = {"ATGCGX", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
		assertThrows(IllegalArgumentException.class, () -> mutantService.isMutant(invalidDna));
	}

	@Test
	void testIrregularDnaMatrix() {
		String[] irregularDna = {"ATGCGA", "CAGTGC", "TTATGT"}; // Faltan filas
		assertThrows(IllegalArgumentException.class, () -> mutantService.isMutant(irregularDna));
	}
	@Test
	void testStatsCalculation() {
		// Limpiar la base de datos antes de la prueba
		dnaSequenceRepository.deleteAll();

		// Insertar ADN de mutantes y humanos
		DnaSequence mutant = new DnaSequence();
		mutant.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
		mutant.setMutant(true);
		dnaSequenceRepository.save(mutant);

		DnaSequence human = new DnaSequence();
		human.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"});
		human.setMutant(false);
		dnaSequenceRepository.save(human);

		// Invocar directamente el método getStats() del controlador
		ResponseEntity<Map<String, Object>> response = statsController.getStats();

		// Obtener el cuerpo de la respuesta
		Map<String, Object> stats = response.getBody();

		// Verificar los resultados
		assertNotNull(stats);
		assertEquals(1L, stats.get("count_mutant_dna"));
		assertEquals(1L, stats.get("count_human_dna"));
		assertEquals(1.0, stats.get("ratio"));
	}
}
