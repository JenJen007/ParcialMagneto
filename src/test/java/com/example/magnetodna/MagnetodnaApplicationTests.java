package com.example.magnetodna;

import com.example.magnetodna.Repository.DnaSequenceRepository;
import com.example.magnetodna.Services.MutantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MagnetodnaApplicationTests {
	@Autowired
	private MutantService mutantService;



	@Test
	void testIsMutant() {
		String[] mutantDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
		assertTrue(mutantService.isMutant(mutantDna));

		String[] humanDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
		assertFalse(mutantService.isMutant(humanDna));
	}
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

}
