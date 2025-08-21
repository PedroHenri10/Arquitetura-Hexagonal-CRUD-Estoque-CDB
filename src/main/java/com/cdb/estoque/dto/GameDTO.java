package com.cdb.estoque.dto;

import com.cdb.estoque.entity.Game;

public class GameDTO {
    private Long id;
    private String titleGame;
    private String plataform;
    private Double price;
    private Integer stock;

    public GameDTO(){

    }

    public GameDTO(Long id, String titleGame, String plataform, Double price, Integer stock) {
        this.id = id;
        this.titleGame = titleGame;
        this.plataform = plataform;
        this.price = price;
        this.stock = stock;
    }

    public GameDTO(Game game) {
        this.id = game.getId();
        this.titleGame = game.getTitleGame();
        this.plataform = game.getPlataform();
        this.price = game.getPrice();
        this.stock = game.getStock();
    }
}
