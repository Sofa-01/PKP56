package com.pizzaro.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Stats {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private int quantity;
    private float price;
    @OneToOne(fetch = FetchType.LAZY)
    private Users manager;

    public Stats() {
        this.quantity = 0;
        this.price = 0;
    }
}
