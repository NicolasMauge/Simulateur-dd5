package com.dd5.conditions;

import com.dd5.enumeration.AvantageEnum;
import com.dd5.enumeration.ConditionEnum;
import com.dd5.attaque.AttaqueEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ConditionService implements IConditionService {
    @Override
    public ConditionEnum getCondition(AttaqueEntity attaque) {
        return ConditionEnum.SANS_CONDITION;
    }

    @Override
    public AvantageEnum quelAvantageAttaquant(Set<ConditionEnum> conditionAttaquantSet,
                                              Set<ConditionEnum> conditionDefenseurSet) {
        AvantageEnum avantage = attaquantAvantagePourConditionDefenseur(conditionDefenseurSet);
        AvantageEnum desavantage = attaquantDesavantagepourConditionAttaquant(conditionAttaquantSet);

        return switch(avantage) {
            case AVANTAGE -> switch(desavantage) {
                case DESAVANTAGE -> AvantageEnum.NEUTRE;
                case AUCUNE_ACTION -> AvantageEnum.AUCUNE_ACTION;
                default -> AvantageEnum.AVANTAGE;
            };
            case NEUTRE -> switch(desavantage) {
                case DESAVANTAGE -> AvantageEnum.DESAVANTAGE;
                case NEUTRE -> AvantageEnum.NEUTRE;
                case AUCUNE_ACTION -> AvantageEnum.AUCUNE_ACTION;
                default -> AvantageEnum.AVANTAGE;
            };
            case AUCUNE_ACTION -> AvantageEnum.AUCUNE_ACTION;
            default -> switch(desavantage) {
                case DESAVANTAGE, NEUTRE -> AvantageEnum.DESAVANTAGE;
                case AUCUNE_ACTION -> AvantageEnum.AUCUNE_ACTION;
                default -> AvantageEnum.NEUTRE;
            };
        };
    }

    private AvantageEnum attaquantAvantagePourConditionDefenseur(Set<ConditionEnum> conditionDefenseurSet) {
        if (conditionDefenseurSet
                .stream()
                .anyMatch(c -> attaquantAvantagePourConditionDefenseur(c) == AvantageEnum.AVANTAGE)) {
            return AvantageEnum.AVANTAGE;
        }

        return AvantageEnum.NEUTRE;
    }

    private AvantageEnum attaquantDesavantagepourConditionAttaquant(Set<ConditionEnum> conditionAttaquantSet) {
        if (conditionAttaquantSet
                .stream()
                .anyMatch(c -> attaquantDesavantagePourConditionAttaquant(c) == AvantageEnum.AUCUNE_ACTION)) {
            return AvantageEnum.AUCUNE_ACTION;
        }

        if (conditionAttaquantSet
                .stream()
                .anyMatch(c -> attaquantDesavantagePourConditionAttaquant(c) == AvantageEnum.DESAVANTAGE)) {
            return AvantageEnum.DESAVANTAGE;
        }

        return AvantageEnum.NEUTRE;
    }

    private AvantageEnum attaquantAvantagePourConditionDefenseur(ConditionEnum conditionDefenseur) {
        return switch(conditionDefenseur) {
            case EMPOISONNE, CHARME, SANS_CONDITION -> AvantageEnum.NEUTRE;
            case AVEUGLE, PARALYSE, ENTRAVE, ETOURDI, PETRIFIE, A_TERRE -> AvantageEnum.AVANTAGE;
            // A_TERRE : on considère que l'assaillant est à < 1,5m
        };
    }

    private AvantageEnum attaquantDesavantagePourConditionAttaquant(ConditionEnum conditionAttaquant) {
        return switch(conditionAttaquant) {
            case EMPOISONNE, ENTRAVE, A_TERRE -> AvantageEnum.DESAVANTAGE;
            case AVEUGLE, ETOURDI, PARALYSE, PETRIFIE, CHARME -> AvantageEnum.AUCUNE_ACTION;
            case SANS_CONDITION -> AvantageEnum.NEUTRE;
        };
    }
    
    @Override
    public boolean peutAgir(Set<ConditionEnum> conditionsAttaquant) {
        return conditionsAttaquant
                .stream()
                .noneMatch(this::estIncapableDAttaquer);
    }
    
    private boolean estIncapableDAttaquer(ConditionEnum conditionAttaquant) {
        return switch(conditionAttaquant) {
            case EMPOISONNE, ENTRAVE, A_TERRE, SANS_CONDITION -> false;
            case AVEUGLE, ETOURDI, PARALYSE, PETRIFIE, CHARME -> true;
        };
    }
}
