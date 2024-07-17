package com.dd5.combat;

import com.dd5.enumeration.AvantageEnum;
import com.dd5.enumeration.StatutEquipeEnum;
import com.dd5.enumeration.StatutProtagonisteEnum;
import com.dd5.ResultatAttaque;
import com.dd5.combat_attaque.IAttaqueService;
import com.dd5.caracteristiques.ICaracteristiquesService;
import com.dd5.combat_etapes.EtatService;
import com.dd5.protagoniste.Equipes;
import com.dd5.protagoniste.ProtagonisteEntity;
import com.dd5.protagonistes.ProtagonisteService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CombatService implements ICombatService {
    private final IAttaqueService attaqueService;
    private final ICaracteristiquesService caracteristiquesService;
    private final ProtagonisteService protagonisteService;
    private final PaireService paireService;
    private final EtatService etatService;

    public CombatService(IAttaqueService attaqueService,
                         ICaracteristiquesService caracteristiquesService,
                         ProtagonisteService protagonisteService, PaireService paireService, EtatService etatService) {
        this.attaqueService = attaqueService;
        this.caracteristiquesService = caracteristiquesService;
        this.protagonisteService = protagonisteService;
        this.paireService = paireService;
        this.etatService = etatService;
    }

    @Override
    public ResultatAttaque lanceAttaque(ProtagonisteEntity attaquant,
                                         ProtagonisteEntity defenseur,
                                         EtatProtagoniste etatAttaquant,
                                         EtatProtagoniste etatDefenseur) {
        //TODO est-ce que avantage ou désavantage en fonction etatAttaquant et etatDefenseur

        return attaqueService.lanceAttaque(
                attaqueService.choisitAttaque(attaquant),
                defenseur.getClasseArmure(),
                caracteristiquesService.getReactionDegats(defenseur),
                AvantageEnum.NEUTRE);
    }

    public TousLesProtagonistes commenceCombat(Equipes equipes) {
        System.out.println(equipes);

        // initialisation des états
        TousLesProtagonistes tousLesProtagonistes = protagonisteService.initialiseTousLesProtagonistes(equipes);
        tousLesProtagonistes.setListePaireAttaquantDefenseur(paireService.getPaireAttaquantDefenseur(equipes));

        List<ProtagonisteEntity> protagonisteList = protagonisteService.findAll();

        while(
                tousLesProtagonistes.getStatutEquipeA() == StatutEquipeEnum.VIVANTE &&
                tousLesProtagonistes.getStatutEquipeB() == StatutEquipeEnum.VIVANTE) {
            // faire un round : faire tous les combats dans les paires
            tousLesProtagonistes = faireUnRound(tousLesProtagonistes, protagonisteList);

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

    private TousLesProtagonistes faireUnRound(
            TousLesProtagonistes tousLesProtagonistes,
            List<ProtagonisteEntity> protagonisteList) {

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


