package com.cdb.estoque.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do jogo é obrigatório")
    private String titleGame;

    @NotBlank(message = "A plataforma é obtigatória")
    private String plataform;

    private Double price;

    private Integer stock;

    public Game(){

    }

    
}
