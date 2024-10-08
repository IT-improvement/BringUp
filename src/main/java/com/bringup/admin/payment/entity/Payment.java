package com.bringup.admin.payment.entity;

import com.bringup.admin.payment.enums.OrderStatus;
import com.bringup.common.enums.RolesType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "item_index")
    private Item itemIdx;

    @Column(name = "user_index")
    private Integer userIdx;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_roles")
    private RolesType roles;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
