package com.dd5.combat;

import com.dd5.enumeration.ConditionEnum;
import com.dd5.enumeration.StatutProtagonisteEnum;

import java.util.Set;

public record EtatProtagoniste(
        int vie,
        Set<ConditionEnum> listeConditions,
        StatutProtagonisteEnum statutProtagoniste) {
}
