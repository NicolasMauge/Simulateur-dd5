package com.dd5.combat;

import com.dd5.protagoniste.Equipes;
import com.dd5.protagoniste.ProtagonisteEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaireService {
    public List<PaireAttaquantDefenseur> getPaireAttaquantDefenseur(Equipes equipes) {
        // definition des paires pour le premier round
        List<ProtagonisteEntity> equipeA = new ArrayList<>(equipes.getEquipeA());
        Collections.shuffle(equipeA);
        LinkedList<ProtagonisteEntity> equipeAL = new LinkedList<>(equipeA);

        List<ProtagonisteEntity> equipeB = new ArrayList<>(equipes.getEquipeB());
        Collections.shuffle(equipeB);
        LinkedList<ProtagonisteEntity> equipeBL = new LinkedList<>(equipeB);

        List<PaireAttaquantDefenseur> paires = new ArrayList<>();
        boolean pairePossible = true;
        while(pairePossible) {
            ProtagonisteEntity pA = equipeAL.pop();
            ProtagonisteEntity pB = equipeBL.pop();

            paires.add(new PaireAttaquantDefenseur(pA, pB));
            paires.add(new PaireAttaquantDefenseur(pB, pA));

            if(equipeAL.isEmpty() || equipeBL.isEmpty()) {
                pairePossible = false;
            }
        }

        if(!equipeAL.isEmpty()) {
            equipeAL.forEach(pA -> paires.add(new PaireAttaquantDefenseur(pA, randomChoice(equipeB))));
        }
        if(!equipeBL.isEmpty()) {
            equipeBL.forEach(pB -> paires.add(new PaireAttaquantDefenseur(pB, randomChoice(equipeA))));
        }
        return paires;
    }

    private ProtagonisteEntity randomChoice(List<ProtagonisteEntity> protagonisteList) {
        Random random = new Random();
        int index = random.nextInt(protagonisteList.size());
        return protagonisteList.get(index);
    }
}
