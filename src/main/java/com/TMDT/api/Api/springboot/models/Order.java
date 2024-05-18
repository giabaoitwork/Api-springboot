package com.TMDT.api.Api.springboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    @JoinColumn(name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_order_customers"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderDetail> orderDetails;
    private String address;
    private int discount;
    private int total;
    private int paymentStatus;
    private LocalDateTime paymentDate;
    private String deliveryId;
    private LocalDateTime createDate;
    private int status;


}
