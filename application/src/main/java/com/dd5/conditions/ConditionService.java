package com.dd5.conditions;

import com.dd5.enumeration.ConditionEnum;
import com.dd5.attaque.AttaqueEntity;
import org.springframework.stereotype.Service;

@Service
public class ConditionService implements IConditionService {
    @Override
    public ConditionEnum getCondition(AttaqueEntity attaque) {
        return ConditionEnum.SANS_CONDITION;
    }
}
