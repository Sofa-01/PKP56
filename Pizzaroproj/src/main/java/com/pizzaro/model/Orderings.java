package com.pizzaro.model;

import com.pizzaro.model.enums.OrderingStatus;
import com.pizzaro.model.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Orderings {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private int quantity;
    private float price;

    private String date;
    private String dateStart;
    private String dateFinish;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private OrderingStatus status;

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Details> details = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users manager;

    public Orderings(Users owner, PaymentType paymentType) {
        this.owner = owner;
        this.status = OrderingStatus.WAITING;
        this.paymentType = paymentType;
        this.date = "";
        this.dateStart = "";
        this.dateFinish = "";
    }

    public String getDate() {
        if (date.isEmpty()) return "";
        String[] res = date.split("T");
        return res[0] + " | " + res[1];
    }

    public String getDateStart() {
        if (dateStart.isEmpty()) return "";
        String[] res = dateStart.split("T");
        return res[0] + " | " + res[1];
    }

    public String getDateFinish() {
        if (dateFinish.isEmpty()) return "";
        String[] res = dateFinish.split("T");
        return res[0] + " | " + res[1];
    }
}
