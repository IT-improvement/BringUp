package com.bringup.admin.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_index", nullable = false, unique = true)
    private Integer itemIndex;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private Integer price;

}
