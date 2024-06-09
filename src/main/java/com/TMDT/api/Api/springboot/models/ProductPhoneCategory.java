package com.TMDT.api.Api.springboot.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_phone_category")
public class ProductPhoneCategory {

    @EmbeddedId
    private ProductPhoneCategoryId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("phoneCategoryId")
    @JoinColumn(name = "phone_category_id")
    private PhoneCategory phoneCategory;
}

