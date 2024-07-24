package com.dd5.entity.effets;

import com.dd5.enumeration.ConditionEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("condition")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class ConditionsEntity extends AbstractEffetEntity {
    private ConditionEnum condition;
}
