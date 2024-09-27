package org.simu.dd5.simulateur.domaine.resultat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class ResultatPlusieursCombat {
	enum GagnantEnum {
		OPPOSANT_A, OPPOSANT_B
	}

	private UUID uuidOpposantA;
	private String nomOpposantA;
	private UUID uuidOpposantB;
	private String nomOpposantB;

	private List<GagnantEnum> gagnantListe;

	public void ajouteUnGagnant(UUID uuidGagnant) {
		if(gagnantListe == null) {
			gagnantListe = new ArrayList<>();
		}

		if (uuidGagnant != null) {
			if(uuidGagnant == uuidOpposantA) {
				gagnantListe.add(GagnantEnum.OPPOSANT_A);
				return;
			}
			if(uuidGagnant == uuidOpposantB) {
				gagnantListe.add(GagnantEnum.OPPOSANT_B);
			}
		}
	}

	public Long nombreGains(UUID uuid) {
		if(uuid == uuidOpposantA) {
			return gagnantListe.stream().filter(g -> g == GagnantEnum.OPPOSANT_A).count();
		}
		if(uuid == uuidOpposantB) {
			return gagnantListe.stream().filter(g -> g == GagnantEnum.OPPOSANT_B).count();
		}
		return null;
	}

	public UUID gagnantGlobal() {
		long nombreVictoireOpposantA = gagnantListe.stream().filter(g -> g == GagnantEnum.OPPOSANT_A).count();
		long nombreVictoireOpposantB = gagnantListe.stream().filter(g -> g == GagnantEnum.OPPOSANT_B).count();

		if(nombreVictoireOpposantA > nombreVictoireOpposantB) {
			return uuidOpposantA;
		}

		if(nombreVictoireOpposantA < nombreVictoireOpposantB) {
			return uuidOpposantB;
		}

		return null;
	}
}
