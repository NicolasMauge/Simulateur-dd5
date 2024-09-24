package org.simu.dd5.simulateur.domaine.opposant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Caracteristique {
	private Integer force;
	private Integer modFOR;

	private Integer dexterite;
	private Integer modDEX;

	private Integer constitution;
	private Integer modCON;

	private Integer intelligence;
	private Integer modINT;

	private Integer sagesse;
	private Integer modSAG;

	private Integer charisme;
	private Integer modCHA;
}
