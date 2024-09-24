package org.simu.dd5.simulateur.domaine.touche;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DDTestReussite {
	private String typeAttaque;
	private String typeArme;
	private String typeDistance;
	private Integer bonusToucher;
	private JetSauvegarde jetSauvegarde;
}
