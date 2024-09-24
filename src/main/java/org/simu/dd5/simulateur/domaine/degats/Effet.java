package org.simu.dd5.simulateur.domaine.degats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Effet {
	private List<Degats> degats;
	private DDTestReussite test;
	private EffetEchec effetEchec;
	private EffetReussite effetReussite;
}
