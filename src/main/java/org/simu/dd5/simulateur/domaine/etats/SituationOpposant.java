package org.simu.dd5.simulateur.domaine.etats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EstCeQueVivantEnum;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.CompetenceEnum;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;

import java.util.Map;

import static org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum.NEUTRE;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SituationOpposant {
	private int vie;
	private Map<EtatEnum, Integer> etatsMap;
	private Map<CompetenceEnum, AvantageEnum> avantageDefenseMap;
	private int attaqueEnEchecSuccessives;

	public boolean estIncapableDAgir() {
		return etatsMap.keySet()
				.stream()
				.anyMatch(c -> switch (c) {
					case EMPOISONNE, ENTRAVE, A_TERRE, SANS_CONDITION, EFFRAYE, AGRIPPE, ASSOURDI, EPUISE, INVISIBLE -> false;
					case AVEUGLE, ETOURDI, PARALYSE, PETRIFIE, CHARME, INCONSCIENT -> true;
				});
	}

	public AvantageEnum aQuelAvantage() {
		// on priorise un désavantage sur un éventuel avantage
		if(aUnEtatQuiLeDesavantage()) {
			return AvantageEnum.DESAVANTAGE;
		}

		if(aUneConditionQuiLAvantage()) {
			return AvantageEnum.AVANTAGE;
		}

		return AvantageEnum.NEUTRE;
	}

	private boolean aUneConditionQuiLAvantage() {
		return etatsMap.keySet()
				.stream()
				.anyMatch(c -> c.equals(EtatEnum.INVISIBLE));
	}

	private boolean aUnEtatQuiLeDesavantage() {
		// on considère que tout désavantage est prioritaire : c'est pourquoi on utilise un anyMatch
		return etatsMap.keySet()
				.stream()
				.anyMatch(c -> switch(c) {
					case EMPOISONNE, ENTRAVE, A_TERRE, EFFRAYE, EPUISE -> true;
					case AVEUGLE, ETOURDI, PARALYSE, PETRIFIE, CHARME, INCONSCIENT, SANS_CONDITION, AGRIPPE, ASSOURDI, INVISIBLE -> false;
				});
	}

	public boolean donneUnAvantagePourAttaquant() {
		return etatsMap.keySet()
				.stream()
				.anyMatch(c -> switch (c) {
					case AVEUGLE, PARALYSE, ENTRAVE, ETOURDI, PETRIFIE, A_TERRE, INCONSCIENT, EPUISE -> true;
					case EMPOISONNE, CHARME, SANS_CONDITION, EFFRAYE, AGRIPPE, ASSOURDI, INVISIBLE -> false;
				});
	}

	public AvantageEnum aUnAvantagePourTestSur(CompetenceEnum c) {
		if(avantageDefenseMap.containsKey(c)) {
			return avantageDefenseMap.get(c);
		}

		return NEUTRE;
	}

	public EstCeQueVivantEnum estNeutralise() {
		if(vie <= 0) {
			return EstCeQueVivantEnum.NEUTRALISE;
		}

		return EstCeQueVivantEnum.VIVANT;
	}

	public void reduitLaVieDe(int reductionVie) {
		this.vie -= reductionVie;
	}

	public void ajouteDesEtats(Map<EtatEnum, Integer> etatsMap) {
		if(etatsMap != null) {
			etatsMap.entrySet().forEach(this::modifieUneClefDeEtatsMap);
		}
	}

	private void modifieUneClefDeEtatsMap(Map.Entry<EtatEnum, Integer> entry) {
		EtatEnum k = entry.getKey();
		Integer v = entry.getValue();

		if (this.etatsMap.containsKey(k)) {
			if(this.etatsMap.get(k) < v) {
				this.etatsMap.put(k, v);
				return;
			}
		}

		this.etatsMap.put(k, v);
	}

	public void ajouteUneAttaqueRatee() {
		attaqueEnEchecSuccessives += 1;
	}

	public void uneAttaqueAPorte() {
		attaqueEnEchecSuccessives = 0;
	}
}
