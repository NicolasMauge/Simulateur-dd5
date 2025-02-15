package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum ConditionEnum {
    EMPOISONNE("empoisonné"),
    AVEUGLE("aveuglé"),
    PARALYSE("paralysé"),
    ENTRAVE("entravé"),
    SANS_CONDITION("pas de condition particulière");

    private final String condition;

    ConditionEnum(String condition) {
        this.condition = condition;
    }

}
