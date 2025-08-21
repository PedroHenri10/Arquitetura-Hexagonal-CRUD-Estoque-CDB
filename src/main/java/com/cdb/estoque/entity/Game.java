package com.cdb.estoque.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

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

    @Positive(message = "O preço deve ser positivo")
    private Double price;

    @PositiveOrZero(message = "O estoque não pode ser negativo")
    private Integer stock;

    public Game(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleGame() {
        return titleGame;
    }

    public void setTitleGame(String titleGame) {
        this.titleGame = titleGame;
    }

    public String getPlataform() {
        return plataform;
    }

    public void setPlataform(String plataform) {
        this.plataform = plataform;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Game(Long id, String titleGame, String plataform, Double price, Integer stock) {
        this.id = id;
        this.titleGame = titleGame;
        this.plataform = plataform;
        this.price = price;
        this.stock = stock;
    }
}
