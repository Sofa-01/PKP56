package com.pizzaro.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderingStatus {
    WAITING("Ожидание"),
    PROCESSED("Обрабатывается"),
    CONFIRMED("Подтверждено"),
    REJECTED("Отклонено"),
    PAID("Оплачено"),
    DELIVERED("Доставлено"),
    ;

    private final String name;
}

