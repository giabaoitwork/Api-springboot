package com.TMDT.api.Api.springboot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity@AllArgsConstructor@NoArgsConstructor@Getter@Setter@ToString
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",
            foreignKey = @ForeignKey(name = "fk_image_product"))
    @JsonIgnore
    private Product product;
    private String url;
}
