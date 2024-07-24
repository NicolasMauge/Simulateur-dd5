package com.dd5.combat_attaque;

import com.dd5.entity.attaque.AttaqueEntity;
import com.dd5.entity.attaque.SubAttaque;
import com.dd5.entity.effets.AbstractEffetEntity;
import com.dd5.entity.effets.ConditionsEntity;
import com.dd5.entity.testdifficulte.AbstractConfrontation;
import com.dd5.entity.testdifficulte.TestAttaquantCA;
import com.dd5.entity.testdifficulte.TestDefenseurEvasion;
import com.dd5.model.aleatoire.enumeration.ResultatTestDDEnum;
import com.dd5.caracteristiques.CaracteristiquesService;
import com.dd5.enumeration.*;
import com.dd5.ILancerDesService;
import com.dd5.ResultatAttaque;
import com.dd5.aleatoire.D20Service;
import com.dd5.entity.effets.AttaqueDeBaseEntity;
import com.dd5.model.attaque.DegatParType;
import com.dd5.model.attaque.DegatParTypeAjuste;
import com.dd5.entity.protagoniste.ProtagonisteEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.dd5.enumeration.MetaTypeDegatEnum.EFFET_NORMAL;

@Service
@AllArgsConstructor
public class AttaqueService implements IAttaqueService {
    private final ILancerDesService des;
    private final D20Service d20Service;
    private final CaracteristiquesService caracteristiquesService;

    @Override
    public AttaqueEntity choisitAttaque(ProtagonisteEntity p) {
        return p.getListeAttaques().getFirst();
    }

    @Override
    public ResultatTestDDEnum quelResultatTest(AbstractConfrontation test,
                                               ProtagonisteEntity defenseur,
                                               AvantageEnum avantage) {
        if (test instanceof TestAttaquantCA) {
            return toucheOuNon((TestAttaquantCA) test, defenseur.getClasseArmure(), avantage);
        }

        // attention, le retour est l'inverse de "toucheOuNon"
        // - il y a des dégâts si le défenseur >rate< son jet
        // - alors que pour "toucheOuNon", c'est l'attaquant qui lance les dés
        ResultatTestDDEnum resultat = estToucheParEffetZoneDD((TestDefenseurEvasion) test, defenseur);
        return switch(resultat) {
            case REUSSITE_CRITIQUE, REUSSITE -> ResultatTestDDEnum.ECHEC;
            case ECHEC, ECHEC_CRITIQUE -> ResultatTestDDEnum.REUSSITE;
        };
    }

    private ResultatTestDDEnum toucheOuNon(TestAttaquantCA test, int classeArmure, AvantageEnum avantage) {
        return d20Service.testDegreDifficulte(test.getBonus(), classeArmure, avantage);
    }

    private ResultatTestDDEnum estToucheParEffetZoneDD(TestDefenseurEvasion testDD, ProtagonisteEntity subitTest) {
        int caracteristiquePourTest = caracteristiquesService.getCaracteristique(subitTest, testDD.getCaracteristique());
        return d20Service.testDegreDifficulte(caracteristiquePourTest, testDD.getDegreDifficulte(), AvantageEnum.NEUTRE);
    }

    @Override
    public ResultatAttaque lanceAttaque(AttaqueEntity attaque,
                                        AvantageEnum avantageAttaquant,
                                        ProtagonisteEntity defenseur
                                        ) {
        // on va voir toutes les subattaques de l'attaque pour les exécuter
        List<SubAttaque> listeSubAttaque = attaque.getListeSubAttaque();

        List<ResultatAttaque> listeResultatAttaque = listeSubAttaque
                .stream()
                .map(subAttaque -> {
                    ResultatTestDDEnum resultat = quelResultatTest(subAttaque.getTest(), defenseur, avantageAttaquant);

                    return switch (resultat) {
                        case ECHEC, ECHEC_CRITIQUE -> attaqueEnEchec(resultat);
                        case REUSSITE -> attaqueReussie(subAttaque, defenseur, false);
                        case REUSSITE_CRITIQUE -> attaqueReussie(subAttaque, defenseur, true);
                    };
                })
                .toList();

        return syntheseResultatsAttaque(listeResultatAttaque);
    }

    private ResultatAttaque syntheseResultatsAttaque(List<ResultatAttaque> listeResultatAttaque) {
        return new ResultatAttaque(
                listeResultatAttaque.getFirst().getResultat(),
                true,
                listeResultatAttaque.stream().map(ResultatAttaque::getDegatParType).flatMap(Set::stream).collect(Collectors.toSet()),
                listeResultatAttaque.stream().map(ResultatAttaque::getTotalDegats).mapToInt(Integer::intValue).sum(),
                listeResultatAttaque.stream().map(ResultatAttaque::getSetConditionsSupplementaires).flatMap(Set::stream).collect(Collectors.toSet())
        );
    }

    private ResultatAttaque attaqueEnEchec(ResultatTestDDEnum resultat) {
        return new ResultatAttaque(
                resultat,
                false,
                new HashSet<>(),
                0,
                new HashSet<>());
    }

    private ResultatAttaque attaqueReussie(SubAttaque subAttaque, ProtagonisteEntity defenseur, boolean reussiteCritique) {
        List<AbstractEffetEntity> effets = subAttaque.getEffets();
        Map<TypeDegatEnum, MetaTypeDegatEnum> reactionDegats = caracteristiquesService.getReactionDegats(defenseur);

        Set<ConditionEnum> listeConditions = new HashSet<>();
        Set<DegatParTypeAjuste> setDegatParTypeAjuste = new HashSet<>();

        effets.forEach(effet -> {
                    if(effet instanceof AttaqueDeBaseEntity) {
                        DegatParType degatParType = reussiteCritique?
                                getDegatParTypeReussiteCritique((AttaqueDeBaseEntity) effet) :
                                getDegatParType((AttaqueDeBaseEntity) effet);

                        setDegatParTypeAjuste.add(getDegatParTypeAjuste(degatParType, reactionDegats));
                    }
                    else {
                        // TODO : il faut gérer les immunités d'état
                        listeConditions.add(((ConditionsEntity) effet).getCondition());
                    }
                });

        return new ResultatAttaque(
                ResultatTestDDEnum.REUSSITE,
                true,
                setDegatParTypeAjuste,
                getTotalDegats(setDegatParTypeAjuste),
                listeConditions);
    }

    private DegatParType getDegatParType(AttaqueDeBaseEntity attaque) {
        //quels sont les dégâts
        return new DegatParType(attaque.getTypeDegat(), des.getValeurDes(attaque.getNombreDeDes()));
    }

    private DegatParType getDegatParTypeReussiteCritique(AttaqueDeBaseEntity attaque) {
        //quels sont les dégâts
        return new DegatParType(attaque.getTypeDegat(), des.getValeurDes(attaque.getNombreDeDes())*2);
    }

    private DegatParTypeAjuste getDegatParTypeAjuste(DegatParType degat, Map<TypeDegatEnum, MetaTypeDegatEnum> reactionDegats) {
        // que se passe-t-il au niveau des types de dégâts
        if(reactionDegats.containsKey(degat.typeDegat())) {
            MetaTypeDegatEnum effetEnnemi = reactionDegats.get(degat.typeDegat());

            return new DegatParTypeAjuste(
                                degat.typeDegat(),
                                degat.degat(),
                                modificationDegat(degat.degat(), effetEnnemi),
                                effetEnnemi);
        }
        else {
            return new DegatParTypeAjuste(
                                degat.typeDegat(),
                                degat.degat(),
                                degat.degat(),
                                EFFET_NORMAL);
        }
    }

    private int modificationDegat(int degat, MetaTypeDegatEnum metaTypeDegat) {
        return switch (metaTypeDegat) {
            case RESISTANCE -> degat/2;
            case VULNERABILITE -> degat*2;
            case IMMUNITE -> 0;
            case EFFET_NORMAL -> degat;
        };
    }

    private int getTotalDegats(Set<DegatParTypeAjuste> degats) {
        return degats
                .stream()
                .map(DegatParTypeAjuste::degatAjuste)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
