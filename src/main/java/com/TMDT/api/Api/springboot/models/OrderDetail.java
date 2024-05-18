package com.TMDT.api.Api.springboot.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",
            foreignKey = @ForeignKey(name = "fk_order_detail_order"))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            foreignKey = @ForeignKey(name = "fk_order_detail_product"))
    private Product product;

    private int quantity;
    private int price;
    private int status;

}
