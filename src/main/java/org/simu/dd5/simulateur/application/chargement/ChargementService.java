package org.simu.dd5.simulateur.application.chargement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.mapper.OpposantJsonToOpposantMapper;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.OpposantJson;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ChargementService {
	private final OpposantJsonToOpposantMapper mapper;

	public List<Opposant> chargeOpposants() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.WRAP_EXCEPTIONS);
		try {
			//JsonNode rootNode = objectMapper.readTree(new File("/Users/nicolasmauge/Documents/01-projets/2024-08_nvle-base-fusionnee/analyse.json"));
			List<OpposantJson> opposantListe = objectMapper.readValue(
					new File("/Users/nicolasmauge/Documents/01-projets/2024-08_nvle-base-fusionnee/analyse.json"),
					new TypeReference<>() {
					} // Spécifie qu'il s'agit d'une liste de Personne
			);

//			OpposantJson opposantJson = opposantListe.stream()
//					.filter(c -> c.getNomCreature().equals("Quasit"))
//					.findFirst()
//					.orElse(new OpposantJson());

//			opposantListe.forEach(o -> {
//				//System.out.println(o);
//				//System.out.println(mapper.mapToOpposant(o));
//				mapper.mapToOpposant(o);
//			});
			return opposantListe
					.stream()
					.map(mapper::mapToOpposant)
					.toList();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}
}