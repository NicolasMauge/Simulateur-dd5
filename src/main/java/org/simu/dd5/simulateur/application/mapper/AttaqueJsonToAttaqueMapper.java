package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.attaque.Attaque;
import org.simu.dd5.simulateur.domaine.attaque.AttaqueJson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class AttaqueJsonToAttaqueMapper {
	private final DDTestReussiteJsonToDDTestReussiteMapper ddTestReussiteJsonToDDTestReussiteMapper;
	private final EffetJsonToEffetMapper effetJsonToEffetMapper;
	private final EffetEchecJsonToEffetEchecMapper effetEchecJsonToEffetEchecMapper;
	private final EffetReussiteJsonToEffetReussiteMapper effetReussiteJsonToEffetReussiteMapper;

	public Attaque mapToAttaque(AttaqueJson attaqueJson) {
		if (attaqueJson == null) {
			return null;
		}

		return new Attaque(
				attaqueJson.getTypeAction(),
				attaqueJson.getNomAttaque(),
				ddTestReussiteJsonToDDTestReussiteMapper.mapToDDTestReussite(attaqueJson.getTest()),
				effetJsonToEffetMapper.mapToEffet(attaqueJson.getEffet()),
				effetEchecJsonToEffetEchecMapper.mapToEffetEchec(attaqueJson.getEffetEchec()),
				effetReussiteJsonToEffetReussiteMapper.mapToEffetReussite(attaqueJson.getEffetReussite())
		);
	}

	public List<Attaque> mapToListeAttaque(List<AttaqueJson> listeAttaqueJson) {
		if (listeAttaqueJson == null) {
			return null;
		}
		return listeAttaqueJson
				.stream()
				.map(this::mapToAttaque)
				.collect(Collectors.toList());
	}
}
