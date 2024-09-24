package org.simu.dd5.simulateur.domaine.attaque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.Effet;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;

@Getter
@AllArgsConstructor
@ToString
public class Attaque {
	private String typeAction;
	private String nomAttaque;
	private DDTestReussite test;
	private Effet effet;
}
