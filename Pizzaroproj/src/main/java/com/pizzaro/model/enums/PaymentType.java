package com.pizzaro.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentType {
    CARD("Картой при получении"),
    CASH("Наличными");
    private final String name;
}

