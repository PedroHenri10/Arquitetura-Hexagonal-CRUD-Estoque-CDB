package com.cdb.estoque.core.domain.model;

import lombok.Getter;
import lombok.Setter;

// ⚠️ IMPORTANTE: O domínio deve ser puro, sem dependências externas.
// Não usar anotações de JPA, Spring, Hibernate, Jackson, etc.
// Aqui usamos Lombok apenas para gerar getters, mas mesmo isso pode ser evitado
// em projetos mais "puristas" de DDD.

@Getter
@Setter
public class Game {
    private Long id;
    private String titleGame;
    private String plataform;
    private String genre;
    private Double price;
    private Integer stock;

    public Game(){

    }

    public Game(Long id, String titleGame, String plataform, String genre, Double price, Integer stock) {
        this.id = id;
        this.titleGame = titleGame;
        this.plataform = plataform;
        this.genre = genre;
        this.price = price;
        this.stock = stock;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.stock += quantity;
    }

    public void decreaseStock(int quantity){
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if(this.stock < quantity){
            throw new IllegalArgumentException("Not enough stock for the game");
        }
        this.stock -= quantity;
    }

}
