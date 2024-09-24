package org.simu.dd5.simulateur.domaine.degats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class EffetEchec {
	private List<Degats> degats;
	private List<EtatEnum> etatListe;
}
