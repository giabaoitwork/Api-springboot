package com.TMDT.api.Api.springboot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor@NoArgsConstructor@Getter@Setter@ToString
@Entity@Table(name = "phone_categories")
public class PhoneCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int status;
    @ManyToMany(mappedBy = "phoneCategories")
    @JsonBackReference
    private List<Product> products;
}
