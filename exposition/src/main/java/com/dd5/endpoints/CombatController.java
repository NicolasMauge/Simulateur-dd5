package com.dd5.endpoints;

import com.dd5.combat.ICombatService;
import com.dd5.combat.TousLesProtagonistes;
import com.dd5.protagoniste.Equipes;
import com.dd5.protagonistes.IEquipesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class CombatController {
    private final ICombatService combatService;
    private final IEquipesService equipesService;

    @PostMapping("/attaque")
    public TousLesProtagonistes lanceCombat(@RequestBody Equipes equipe) {
        return combatService.commenceCombat(equipesService.reconstruireEquipes(equipe));
    }
}
