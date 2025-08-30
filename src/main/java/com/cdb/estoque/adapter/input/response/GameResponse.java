package com.cdb.estoque.adapter.input.response;

public record GameResponse{
    Long id,
    String titleGame,
    String plataform,
    String genre,
    Double price,
    Integer stock
}{}
