package com.pizzaro.model;

import com.pizzaro.model.enums.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Products {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String photo;
    private float price;
    private int weight;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(length = 5000)
    private String structure;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Details> details;

    public Products(String name, String photo, int weight, float price, String structure, Category category) {
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.weight = weight;
        this.structure = structure;
        this.category = category;
    }

    public void set(String name, int weight, float price, String structure,Category category) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.structure = structure;
        this.category = category;
    }
}
