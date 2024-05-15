package com.TMDT.api.Api.springboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

}
