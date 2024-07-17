package com.dd5.combat_attaque;

import com.dd5.enumeration.*;
import com.dd5.LancerDesService;
import com.dd5.ResultatAttaque;
import com.dd5.aleatoire.D20Service;
import com.dd5.attaque.AttaqueEntity;
import com.dd5.attaque.DegatParType;
import com.dd5.attaque.DegatParTypeAjuste;
import com.dd5.conditions.IConditionService;
import com.dd5.protagoniste.ProtagonisteEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dd5.enumeration.MetaTypeDegatEnum.EFFETNORMAL;

@Service
@AllArgsConstructor
public class AttaqueService implements IAttaqueService {
    private final LancerDesService des;
    private final D20Service d20Service;
    private final IConditionService conditionService;

    @Override
    public AttaqueEntity choisitAttaque(ProtagonisteEntity p) {
        return p.getListeAttaques().getFirst();
    }

    @Override
    public ResultatTestDDEnum toucheOuNon(AttaqueEntity attaque, int classeArmure, AvantageEnum avantage) {
        return d20Service.testDegreDifficulte(attaque.getHitBonus(), classeArmure, avantage);
    }

    @Override
    public ResultatAttaque lanceAttaque(AttaqueEntity attaque,
                                        int classeArmureDefenseur,
                                        Map<TypeDegatEnum, MetaTypeDegatEnum> reactionDegatsDefenseur,
                                        AvantageEnum avantageAttaquant) {
        //est-ce que l'attaque réussit
        return switch (toucheOuNon(attaque, classeArmureDefenseur, avantageAttaquant)) {
            case REUSSITE_CRITIQUE -> {
                List<DegatParTypeAjuste> degatsAjustes = getDegatParTypeAjuste(
                        getDegatParTypeReussiteCritique(attaque),
                        reactionDegatsDefenseur);

                ConditionEnum condition = conditionService.getCondition(attaque);

                yield new ResultatAttaque(
                        ResultatTestDDEnum.REUSSITE_CRITIQUE,
                        true,
                        degatsAjustes,
                        getTotalDegats(degatsAjustes),
                        condition);
            }
            case REUSSITE -> {
                List<DegatParTypeAjuste> degatsAjustes = getDegatParTypeAjuste(
                    getDegatParType(attaque),
                    reactionDegatsDefenseur);

                ConditionEnum condition = conditionService.getCondition(attaque);

                yield new ResultatAttaque(
                        ResultatTestDDEnum.REUSSITE,
                        true,
                        degatsAjustes,
                        getTotalDegats(degatsAjustes),
                        condition);
            }
            case ResultatTestDDEnum resultatTest -> new ResultatAttaque(
                    resultatTest,
                    false,
                    new ArrayList<>(),
                    0,
                    ConditionEnum.SANS_CONDITION);
        };
    }

    private List<DegatParType> getDegatParType(AttaqueEntity attaque) {
        //quels sont les dégâts
        return attaque.getAttaqueParDegatList()
                .stream()
                .map(a -> new DegatParType(
                                a.getTypeDegat(),
                                des.getValeurDes(a.getNombreDeDes())
                        )
                )
                .toList();
    }

    private List<DegatParType> getDegatParTypeReussiteCritique(AttaqueEntity attaque) {
        //quels sont les dégâts
        return attaque.getAttaqueParDegatList()
                .stream()
                .map(a -> new DegatParType(
                                a.getTypeDegat(),
                                des.getValeurDes(a.getNombreDeDes())*2
                        )
                )
                .toList();
    }

    private List<DegatParTypeAjuste> getDegatParTypeAjuste(List<DegatParType> degats, Map<TypeDegatEnum, MetaTypeDegatEnum> reactionDegats) {
        // que se passe-t-il au niveau des types de dégâts
        return degats
                .stream()
                .map(d -> {
                    TypeDegatEnum typeDegat = d.typeDegat();
                    if(reactionDegats.containsKey(typeDegat)) {
                        MetaTypeDegatEnum effetEnnemi = reactionDegats.get(typeDegat);
                        return new DegatParTypeAjuste(
                                typeDegat,
                                d.degat(),
                                modificationDegat(d.degat(), effetEnnemi),
                                effetEnnemi);
                    }
                    else {
                        return new DegatParTypeAjuste(
                                typeDegat,
                                d.degat(),
                                d.degat(),
                                EFFETNORMAL);
                    }
                })
                .toList();
    }

    private int modificationDegat(int degat, MetaTypeDegatEnum metaTypeDegat) {
        return switch (metaTypeDegat) {
            case RESISTANCE -> degat/2;
            case VULNERABILITE -> degat*2;
            case IMMUNITE -> 0;
            case EFFETNORMAL -> degat;
        };
    }

    private int getTotalDegats(List<DegatParTypeAjuste> degats) {
        return degats
                .stream()
                .map(DegatParTypeAjuste::degatAjuste)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
