package org.simu.dd5.simulateur.application.chargement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.application.mapper.OpposantJsonToOpposantMapper;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.simu.dd5.simulateur.domaine.opposant.OpposantJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class ChargementService {
	private static final Logger logger = LoggerFactory.getLogger(ChargementService.class);

	private final OpposantJsonToOpposantMapper mapper;

	private ObjectMapper objectMapper;

	public List<Opposant> chargeOpposants() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.WRAP_EXCEPTIONS);

		List<OpposantJson> opposantListe = new ArrayList<>();

		try {
			JsonNode rootNode = objectMapper.readTree(new File("/Users/nicolasmauge/Documents/01-projets/2024-08_nvle-base-fusionnee/analyse.json"));
			rootNode.forEach(r -> opposantListe.add(convertitJson(r)));

			return opposantListe
					.stream()
					.filter(Objects::nonNull)
					.map(mapper::mapToOpposant)
					.filter(this::filtreOpposant)
					.toList();
		} catch (IOException e) {
			System.out.println("Le chargement ne marche pas");
			return null;
		}
	}

	private OpposantJson convertitJson(JsonNode rootNode) {
		try {
			return getObjectMapper().treeToValue(rootNode, OpposantJson.class);
		} catch (JsonProcessingException e) {
			logger.error("Erreur sur {} : ", rootNode, e);
			return null;
		}
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}

		return objectMapper;
	}

	private boolean filtreOpposant(Opposant opposant) {
		if (opposant.getClasseArmure() == null) {
			logger.warn("On enlève le monstre {} car sa classe d'armure est vide", opposant.getNom());
			return false;
		}

		if (opposant.getListeAttaques().stream().anyMatch(
				a -> {
					if (a.getTest() != null && a.getTest().getBonusToucher() != null) { // il y a une attaque avec toucher
						// mais pas d'effet, ou pas de dégâts
						return a.getEffet() == null || a.getEffet().getDegats() == null || a.getEffet().getDegats().isEmpty();
					}
					return false;})) {
			logger.warn("On enlève le monstre {} car il y a des problèmes sur les effets suite une attaque avec toucher", opposant.getNom());

			return false;
		}

		if(opposant.getListeAttaques().stream().anyMatch(
				a -> {
					if(a.getEffet() != null && a.getEffet().getEffetEchec() != null) {
						return a.getEffet().getEffetEchec().getDegats() == null && (a.getEffet().getEffetEchec().getEtatSet() == null || a.getEffet().getEffetEchec().getEtatSet().isEmpty());
					}
					return false;
				}
		)) {
			logger.warn("On enlève le monstre {} car il y a des problèmes sur les effets échec dans effet d'une attaque toucher", opposant.getNom());

			return false;
		}

		if(opposant.getListeAttaques().stream().anyMatch(
				a -> a.getEffet() != null && a.getEffet().getTest() != null && a.getEffet().getTest().getJetSauvegarde() == null
		)) {
			logger.warn("On enlève le monstre {} car il manque le jet de sauvegarde dans le test", opposant.getNom());

			return false;
		}

		System.out.println(opposant.getNom());

		if(opposant.getListeAttaques().stream().anyMatch(
				a -> a.getEffet() != null && a.getEffet().getTest() != null && a.getEffet().getEffetEchec() == null
		)) {
			return false;
		}

		return true;
	}
}