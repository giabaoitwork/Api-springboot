package com.TMDT.api.Api.springboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity@AllArgsConstructor@NoArgsConstructor@Getter@Setter
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private int role;
    private int point;
    private int status;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Address> addresses;

}
