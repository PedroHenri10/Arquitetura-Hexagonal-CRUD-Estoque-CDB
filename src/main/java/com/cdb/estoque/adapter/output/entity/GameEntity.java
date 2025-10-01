package com.cdb.estoque.adapter.output.entity;


import jakarta.persistence.*;

/**
 * ⚠️ Esta classe representa a ENTIDADE de persistência no banco de dados.
 * Ela pertence à camada de infraestrutura (adapter/output) e serve apenas para armazenar dados.
 *
 * 📌 Observações:
 * 1. Não deve conter lógica de negócio.
 * 2. Pode usar anotações do JPA (@Entity, @Table, @Id etc.).
 * 3. Pode ter getters e setters (ou Lombok) à vontade, porque é só um DTO de persistência.
 * 4. Deve ser mapeada para o domínio através de um mapper/converter.
 */

@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titleGame;

    private String plataform;

    private String genre;

    private Double price;

    private Integer stock;

    public GameEntity(){

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

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public GameEntity(Long id, String titleGame, String plataform, String genre, Double price, Integer stock) {
        this.id = id;
        this.titleGame = titleGame;
        this.plataform = plataform;
        this.genre = genre;
        this.price = price;
        this.stock = stock;
    }
}
