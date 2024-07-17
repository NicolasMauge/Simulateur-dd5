package com.dd5.combat;

import com.dd5.ResultatAttaque;
import com.dd5.protagoniste.Equipes;
import com.dd5.protagoniste.ProtagonisteEntity;

public interface ICombatService {
    ResultatAttaque lanceAttaque(ProtagonisteEntity attaquant,
                                 ProtagonisteEntity defenseur,
                                 EtatProtagoniste etatAttaquant,
                                 EtatProtagoniste etatDefenseur);

    TousLesProtagonistes commenceCombat(Equipes equipe);
}
