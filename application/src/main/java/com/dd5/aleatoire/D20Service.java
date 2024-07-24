package com.dd5.aleatoire;


import com.dd5.enumeration.AvantageEnum;
import com.dd5.model.aleatoire.enumeration.ResultatTestDDEnum;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class D20Service implements ID20Service {
    @Override
    public ResultatTestDDEnum testDegreDifficulte(int bonus, int degreDifficulte, AvantageEnum avantage) {
        return switch(lanceUnDeAvecAvantage(avantage)) {
            case 1 -> ResultatTestDDEnum.ECHEC_CRITIQUE;
            case 20 -> ResultatTestDDEnum.REUSSITE_CRITIQUE;
            case Integer valeur -> (valeur + bonus >= degreDifficulte)? ResultatTestDDEnum.REUSSITE:ResultatTestDDEnum.ECHEC;
        };
    }

    private Integer lanceUnDe() {
        Random r = new Random();
        return r.nextInt(20) + 1;
    }

    private Integer lanceUnDeAvecAvantage(AvantageEnum avantage) {
        return switch(avantage) {
            case AVANTAGE -> Math.max(lanceUnDe(), lanceUnDe());
            case DESAVANTAGE -> Math.min(lanceUnDe(), lanceUnDe());
            case NEUTRE -> lanceUnDe();
            case AUCUNE_ACTION -> 0;
        };
    }
}
