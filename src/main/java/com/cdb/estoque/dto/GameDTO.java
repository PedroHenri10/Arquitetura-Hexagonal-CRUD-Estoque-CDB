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
}
