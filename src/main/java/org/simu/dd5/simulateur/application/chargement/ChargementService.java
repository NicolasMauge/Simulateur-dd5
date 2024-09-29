package org.simu.dd5.simulateur.application.chargement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.commun.ToStringPretty;
import org.simu.dd5.simulateur.application.mapper.OpposantJsonToOpposantMapper;
import org.simu.dd5.simulateur.domaine.chargement.ErreurMapping;
import org.simu.dd5.simulateur.domaine.chargement.ResultatMapping;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.OpposantJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@Service
public class ChargementService {
	private static final Logger logger = LoggerFactory.getLogger(ChargementService.class);
	private static final String NOM_FICHIER = "/Users/nicolasmauge/Documents/01-projets/2024-08_nvle-base-fusionnee/analyse.json";

	private final OpposantJsonToOpposantMapper mapper;
	private final ToStringPretty toStringPretty;

	private ObjectMapper objectMapper;

	public List<Opposant> chargeOpposants() {
		JsonNode rootNode = chargeFichier(NOM_FICHIER);

		if(rootNode == null) {
			return null;
		}

		// les nodes qui ne peuvent pas être convertis
		List<JsonNode> nodes = impossiblesAConvertir(rootNode);

		System.out.println("---------------------------------------");
		System.out.println("Impossibles à convertir " + nodes);

		// ce qui a pu être converti en OpposantJson
		List<OpposantJson> opposantJsonListe = new ArrayList<>();
		rootNode.forEach(r -> opposantJsonListe.add(convertitJson(r)));

		// ce qui n'a pas pu être converti en Opposant
		ResultatMapping resultatMapping = convertitEnEntity(opposantJsonListe);

		System.out.println("---------------------------------------");
		System.out.println("Erreurs de mappings : ");
		System.out.println(resultatMapping.getErreurMappingListe());

		// filtrage
		logger.info("Il y a {} créatures exclues car non complètes", resultatMapping.getOpposantListe().stream()
				.filter(o->!o.complet()).toList().size());

		toStringPretty.save_incomplet(resultatMapping.getOpposantListe()
				.stream()
				.filter(o->!o.complet())
				.toList());

		return resultatMapping.getOpposantListe().stream().filter(Opposant::complet).toList();
	}

	private ResultatMapping convertitEnEntity(List<OpposantJson> opposantJsonListe) {
		List<Opposant> opposantListe = new ArrayList<>();
		List<ErreurMapping> erreurMappingListe = new ArrayList<>();
		opposantJsonListe.forEach(oj -> {
			try {
				opposantListe.add(mapper.mapToOpposant(oj)); // Méthode de conversion
			} catch (Exception e) {
				erreurMappingListe.add(new ErreurMapping(e, oj));
			}
		});

		return new ResultatMapping(opposantListe, erreurMappingListe);
	}

	private List<JsonNode> impossiblesAConvertir(JsonNode jsonNode) {
		List<JsonNode> opposantJsonListeImpossibleAConvertir = new ArrayList<>();
		for(JsonNode node : jsonNode) {
			try {
				getObjectMapper().treeToValue(node, OpposantJson.class);
			} catch (JsonProcessingException e) {
				opposantJsonListeImpossibleAConvertir.add(node);
			}
		}

		return opposantJsonListeImpossibleAConvertir;
	}

	private JsonNode chargeFichier(String fichier) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.WRAP_EXCEPTIONS);

		try {
			return objectMapper.readTree(new File(fichier));
		} catch (IOException e) {
            logger.error("Le fichier analyse.json n'a pas pu être chargé");
			return null;
        }
    }

	private OpposantJson convertitJson(JsonNode rootNode) {
		try {
			return getObjectMapper().treeToValue(rootNode, OpposantJson.class);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}

		return objectMapper;
	}
}