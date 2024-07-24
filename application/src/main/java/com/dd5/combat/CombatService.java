package com.dd5.combat;

import com.dd5.model.aleatoire.enumeration.ResultatTestDDEnum;
import com.dd5.combat_etapes.IEtatService;
import com.dd5.conditions.IConditionService;
import com.dd5.enumeration.*;
import com.dd5.ResultatAttaque;
import com.dd5.combat_attaque.IAttaqueService;
import com.dd5.caracteristiques.ICaracteristiquesService;
import com.dd5.entity.protagoniste.Equipes;
import com.dd5.entity.protagoniste.ProtagonisteEntity;
import com.dd5.model.combat.EtatProtagoniste;
import com.dd5.model.combat.EvolutionProtagoniste;
import com.dd5.model.combat.TousLesProtagonistes;
import com.dd5.protagonistes.ProtagonisteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CombatService implements ICombatService {
    private final IAttaqueService attaqueService;
    private final ProtagonisteService protagonisteService;
    private final IPaireService paireService;
    private final IEtatService etatService;
    private final IConditionService conditionService;

    @Override
    public ResultatAttaque lanceAttaque(ProtagonisteEntity attaquant,
                                         ProtagonisteEntity defenseur,
                                         EtatProtagoniste etatAttaquant,
                                         EtatProtagoniste etatDefenseur) {

        Set<ConditionEnum> conditionsAttaquant = etatAttaquant.setConditions();
        Set<ConditionEnum> conditionsDefenseur = etatDefenseur.setConditions();

        // est-ce que l'attaquant peut attaquer
        if (!conditionService.peutAgir(conditionsAttaquant)) {
            return new ResultatAttaque(
                    ResultatTestDDEnum.ECHEC,
                    false,
                    new HashSet<>(),
                    0,
                    new HashSet<>());
        }

        AvantageEnum avantageAttaquant = conditionService.quelAvantageAttaquant(conditionsAttaquant, conditionsDefenseur);

        return attaqueService.lanceAttaque(
                                        attaqueService.choisitAttaque(attaquant),
                                        avantageAttaquant,
                                        defenseur
                                        );
    }

    public TousLesProtagonistes commenceCombat(Equipes equipes) {
        System.out.println(equipes);

        // initialisation des états
        TousLesProtagonistes tousLesProtagonistes = protagonisteService.initialiseTousLesProtagonistes(equipes);
        tousLesProtagonistes.setListePaireAttaquantDefenseur(paireService.getPaireAttaquantDefenseur(equipes));

        while(
                tousLesProtagonistes.getStatutEquipeA() == StatutEquipeEnum.VIVANTE &&
                tousLesProtagonistes.getStatutEquipeB() == StatutEquipeEnum.VIVANTE) {
            // faire un round : faire tous les combats dans les paires
            tousLesProtagonistes = faireUnRound(tousLesProtagonistes);

            // vérifier que parmi les paires sont toujours valables sinon les modifier

            // vérifier si les équipes sont encore opérationnelles
            if(equipeAEstVivante(tousLesProtagonistes)) {
                tousLesProtagonistes.setStatutEquipeA(StatutEquipeEnum.VIVANTE);
            } else {
                tousLesProtagonistes.setStatutEquipeA(StatutEquipeEnum.NEUTRALISEE);
            }

            if(equipeBEstVivante(tousLesProtagonistes)) {
                tousLesProtagonistes.setStatutEquipeA(StatutEquipeEnum.VIVANTE);
            } else {
                tousLesProtagonistes.setStatutEquipeA(StatutEquipeEnum.NEUTRALISEE);
            }
        }

        System.out.println(tousLesProtagonistes.getStatutEquipeA());
        System.out.println(tousLesProtagonistes.getStatutEquipeB());

        System.out.println(tousLesProtagonistes);

        return tousLesProtagonistes;
    }

    @Override
    public TousLesProtagonistes faireUnRound(TousLesProtagonistes tousLesProtagonistes) {
        tousLesProtagonistes.getListePaireAttaquantDefenseur()
                .forEach(p -> {
                    ProtagonisteEntity attaquant = p.attaquant();
                    ProtagonisteEntity defenseur = p.defenseur();

                    ResultatAttaque resultatAttaque = lanceAttaque(
                            attaquant,
                            defenseur,
                            tousLesProtagonistes.getEvolutionParProtagoniste().get(attaquant).getEtatProtagonisteList().getLast(),
                            tousLesProtagonistes.getEvolutionParProtagoniste().get(defenseur).getEtatProtagonisteList().getLast()
                    );

                    // TODO gérer les effets sur les conditions

                    EvolutionProtagoniste evolutionDefenseur = tousLesProtagonistes.getEvolutionParProtagoniste().get(defenseur);

                    evolutionDefenseur.setEtatProtagonisteList(
                            etatService.nouvelEtatSuiteAttaque(resultatAttaque, evolutionDefenseur.getEtatProtagonisteList()));
                });

        return tousLesProtagonistes;
    }

    private boolean equipeAEstVivante(TousLesProtagonistes tousLesProtagonistes) {
        return tousLesProtagonistes.getEquipes().getEquipeA()
                .stream()
                .anyMatch(p -> tousLesProtagonistes.getEvolutionParProtagoniste()
                        .get(p)
                        .getEtatProtagonisteList()
                        .getLast()
                        .statutProtagoniste()==StatutProtagonisteEnum.VIVANT);
    }

    private boolean equipeBEstVivante(TousLesProtagonistes tousLesProtagonistes) {
        return tousLesProtagonistes.getEquipes().getEquipeB()
                .stream()
                .anyMatch(p -> tousLesProtagonistes.getEvolutionParProtagoniste()
                        .get(p)
                        .getEtatProtagonisteList()
                        .getLast()
                        .statutProtagoniste()==StatutProtagonisteEnum.VIVANT);
    }
}


