package com.dd5.conditions;

import com.dd5.enumeration.ConditionEnum;
import com.dd5.attaque.AttaqueEntity;

public interface IConditionService {
    ConditionEnum getCondition(AttaqueEntity attaque);
}
