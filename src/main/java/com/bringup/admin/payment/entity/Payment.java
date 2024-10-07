package com.bringup.admin.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_index", nullable = false, unique = true)
    private Integer orderIndex;

    @Column(name = "item_index")
    private Long itemIdx;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private Long itemPrice;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;
}
