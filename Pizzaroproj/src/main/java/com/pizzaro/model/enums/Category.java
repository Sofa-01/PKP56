package com.pizzaro.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    PIZZA("Пицца"),
    SOUP("Суп"),
    SOUSE("Соус"),
    DRINK("Напиток"),
    SALAD("Салат"),
    ;

    private final String name;


}

