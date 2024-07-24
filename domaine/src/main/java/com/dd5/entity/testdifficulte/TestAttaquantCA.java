package com.dd5.entity.testdifficulte;

import com.dd5.enumeration.TypeArmeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("attaque")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class TestAttaquantCA extends AbstractConfrontation {
    private TypeArmeEnum typeArme;
    private int bonus;
}
