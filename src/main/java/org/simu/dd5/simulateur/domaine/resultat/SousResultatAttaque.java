package org.simu.dd5.simulateur.domaine.resultat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;
import org.simu.dd5.simulateur.domaine.resultat.typeenum.ResultatTestDDEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@ToString
public class SousResultatAttaque {
	private ResultatTestDDEnum resultatAttaque;

	private Map<TypeDegatEnum, Integer> degatParType;
	private Map<TypeDegatEnum, Integer> degatParTypeEnFonctionTypeReussiteEtDefenseur;
	private Map<EtatEnum, Integer> etatsSupplementairesMap;

	public int getTotalDegats() {
		if(degatParTypeEnFonctionTypeReussiteEtDefenseur == null) {
			return 0;
		}
		return degatParTypeEnFonctionTypeReussiteEtDefenseur.values().stream().mapToInt(Integer::intValue).sum();
	}

	public SousResultatAttaque moitie() {
		return new SousResultatAttaque(
				resultatAttaque,

				degatParType.entrySet()
						.stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()/2)),

				degatParTypeEnFonctionTypeReussiteEtDefenseur.entrySet()
						.stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()/2)),

				etatsSupplementairesMap
		);
	}

	public static SousResultatAttaque REUSSIE_SANS_EFFET() {
		return new SousResultatAttaque(ResultatTestDDEnum.REUSSITE, null, null, null);
	}

	public SousResultatAttaque ajoutDesEtats(List<EtatEnum> etatsListe) {
		etatsSupplementairesMap = etatsListe
				.stream()
				.collect(Collectors.toMap(
						e -> e,
						_ -> 1));

		return this;
	}
}
