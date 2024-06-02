package com.TMDT.api.Api.springboot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cart_details")
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    @JoinColumn(name = "product_id",
            foreignKey = @ForeignKey(name = "fk_cart_detail_product"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


    @JoinColumn(name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_cart_detail_customer"))
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "customer-cart")
    private Customer customer;
    private int quantity;
    private int status;
}
