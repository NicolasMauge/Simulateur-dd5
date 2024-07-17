package com.dd5.conditions;

import com.dd5.enumeration.AvantageEnum;
import com.dd5.enumeration.ConditionEnum;
import com.dd5.attaque.AttaqueEntity;

import java.util.Set;

public interface IConditionService {
    ConditionEnum getCondition(AttaqueEntity attaque);
    boolean peutAgir(Set<ConditionEnum> conditionsAttaquant);
    AvantageEnum quelAvantageAttaquant(Set<ConditionEnum> conditionAttaquantList,
                                       Set<ConditionEnum> conditionDefenseurList);
}
