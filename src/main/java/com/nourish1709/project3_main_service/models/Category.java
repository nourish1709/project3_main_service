package com.nourish1709.project3_main_service.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "account_id",
            referencedColumnName = "id")
    private Account account;
}