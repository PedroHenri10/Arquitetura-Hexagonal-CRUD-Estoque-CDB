package com.cdb.estoque.core.domain.model;

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

    public String getGenre() {return genre;}

    public void setGenre(String genre){
        this.genre = genre;
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

}
