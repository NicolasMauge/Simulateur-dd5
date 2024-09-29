package org.simu.dd5.simulateur.domaine.opposant;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.simu.dd5.simulateur.domaine.attaque.Attaque;
import org.simu.dd5.simulateur.domaine.etats.SituationOpposant;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EstCeQueVivantEnum;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.AvantageEnum;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.CaracteristiqueEnum;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.CompetenceEnum;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeEffetDegatsEnum;
import org.simu.dd5.simulateur.domaine.general.typeenum.TypeRaceEnum;

@AllArgsConstructor
@Getter
@ToString
public class Opposant {
	public static final int INITIAL_ELO = 1500;

	private UUID uuid;

	private String nom;
	private TypeRaceEnum espece;

	private Integer classeArmure;
	private Integer pointDeVie;
	private String pointDeVieDes;
	private String vitesse;

	private Caracteristique caracteristiques;

	private Map<CompetenceEnum, Integer> competenceList;

	private int bonusDeMaitrise;

	private Map<TypeDegatEnum, TypeEffetDegatsEnum> effetDegatsEnFonctionType;

	private List<EtatEnum> immuniteEtats;

	private int dangerosite;
	@Setter
	private double dangerositeNormee;

	private List<Attaque> listeAttaques;

	private SituationOpposant situationOpposant;

	@Setter
	private int classementELO;

	public Integer getValeurCaracteristique(CaracteristiqueEnum caracteristique) {
		return switch(caracteristique) {
			case FOR -> caracteristiques.getModFOR();
			case DEX -> caracteristiques.getModDEX();
			case CON -> caracteristiques.getModCON();
			case INT -> caracteristiques.getModINT();
			case SAG -> caracteristiques.getModSAG();
			case CHA -> caracteristiques.getModCHA();
		};
	}

	public Integer getValeurCompetence(CompetenceEnum c) {
		if(competenceList.containsKey(c)) {
			return competenceList.get(c);
		}

		return getValeurCaracteristique(c.quelSubstitut());
	}

	public boolean estIncapableDAgir() {
		return situationOpposant.estIncapableDAgir();
	}

	public AvantageEnum aUnAvantagePourTestSur(CompetenceEnum c) {
		return situationOpposant.aUnAvantagePourTestSur(c);
	}

	public AvantageEnum aQuelAvantage() {
		return situationOpposant.aQuelAvantage();
	}

	public boolean donneUnAvantagePourAttaquant() {
		return situationOpposant.donneUnAvantagePourAttaquant();
	}

	public boolean estNeutralise() {
		return situationOpposant.estNeutralise() == EstCeQueVivantEnum.NEUTRALISE;
	}

	public Integer quelDegatAjuste(Map.Entry<TypeDegatEnum, Integer> entryTypeDegatEtDegat) {
		TypeDegatEnum typeDegatEnum = entryTypeDegatEtDegat.getKey();

		if(!effetDegatsEnFonctionType.containsKey(typeDegatEnum)) {
			return entryTypeDegatEtDegat.getValue();
		}

		return switch (effetDegatsEnFonctionType.get(typeDegatEnum)) {
			case RESISTANCE -> entryTypeDegatEtDegat.getValue()/2;
			case VULNERABILITE -> entryTypeDegatEtDegat.getValue()*2;
			case IMMUNITE -> 0;
			case EFFET_NORMAL -> entryTypeDegatEtDegat.getValue();
		};
	}

	public void reinitialiseSituation() {
		situationOpposant = new SituationOpposant(pointDeVie!=null?pointDeVie:-1, new HashMap<>(), new HashMap<>(), 0);
	}

	public boolean complet() {
		if(classeArmure == null) {
			return false;
		}

		List<Attaque> reliquat = listeAttaques
				.stream()
				.filter(Attaque::estCoherente)
				.toList();

		return !reliquat.isEmpty();
	}

	public boolean aAuMoinsUneAttaqueAvecToucher() {
		return listeAttaques.stream().anyMatch(Attaque::estUneAttaqueAvecToucher);
	}

	public boolean aAuMoinsUneAttaque() {
		return listeAttaques.stream().anyMatch(Attaque::estUneAttaque);
	}
}

