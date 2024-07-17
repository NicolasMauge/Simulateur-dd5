package com.dd5.combat;

import com.dd5.enumeration.ConditionEnum;
import com.dd5.attaque.DegatParTypeAjuste;

import java.util.List;

public record Evenement(int id, List<DegatParTypeAjuste> degat, ConditionEnum condition) {
}
