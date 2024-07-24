package com.dd5.aleatoire;

import com.dd5.enumeration.AvantageEnum;
import com.dd5.model.aleatoire.enumeration.ResultatTestDDEnum;

public interface ID20Service {
    ResultatTestDDEnum testDegreDifficulte(int bonus, int degreDifficulte, AvantageEnum avantage);
}
