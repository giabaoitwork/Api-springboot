package com.TMDT.api.Api.springboot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double price;

    @Column
    private double discount;
    @Column
    private int quantity;
    @Column
    private int sold;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;
    @Column
    private int status; // 0: ok, 1: hidden

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "product_phone_category",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "phone_category_id")}
    )
    @JsonManagedReference
    private List<PhoneCategory> phoneCategories;

    public Product() {
    }


}
