package com.dd5.combat;

import com.dd5.ResultatAttaque;
import com.dd5.entity.protagoniste.Equipes;
import com.dd5.entity.protagoniste.ProtagonisteEntity;
import com.dd5.model.combat.EtatProtagoniste;
import com.dd5.model.combat.TousLesProtagonistes;

public interface ICombatService {
    ResultatAttaque lanceAttaque(ProtagonisteEntity attaquant,
                                 ProtagonisteEntity defenseur,
                                 EtatProtagoniste etatAttaquant,
                                 EtatProtagoniste etatDefenseur);

    TousLesProtagonistes commenceCombat(Equipes equipe);

    TousLesProtagonistes faireUnRound(TousLesProtagonistes tousLesProtagonistes);
}
