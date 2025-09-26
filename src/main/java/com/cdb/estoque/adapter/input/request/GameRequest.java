package com.cdb.estoque.adapter.input.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.Objects;


/**
 * DTO (Data Transfer Object) de entrada para criação ou atualização de Game.
 *
 * 📌 Observações:
 *  Pode usar Lombok para reduzir boilerplate (getters, construtores, toString).
 */

public class GameRequest {

    @NotBlank(message = "O título do jogo é obrigatório.")
    @Size(min = 2, max = 100, message = "O título deve ter entre 2 e 100 caracteres.")
    private String titleGame;

    @NotBlank(message = "A plataforma é obrigatória.")
    @Size(max = 50, message = "A plataforma não deve exceder 50 caracteres.")
    private String plataform;

    @Size(max = 50, message = "O gênero não deve exceder 50 caracteres.")
    private String genre;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private Double price;

    @NotNull(message = "O estoque é obrigatório.")
    @PositiveOrZero(message = "O estoque não pode ser um número negativo.")
    private Integer stock;

    public GameRequest(String titleGame, String plataform, String genre, Double price, Integer stock) {
        this.titleGame = titleGame;
        this.plataform = plataform;
        this.genre = genre;
        this.price = price;
        this.stock = stock;
    }

    public String getTitleGame() { return titleGame; }
    public String getPlataform() { return plataform; }
    public String getGenre() { return genre; }
    public Double getPrice() { return price; }
    public Integer getStock() { return stock; }

    @Override
    public String toString() {
        return "GameRequest[" +
                "titleGame='" + titleGame + '\'' +
                ", plataform='" + plataform + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ']';
    }
}