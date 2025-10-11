package com.cdb.estoque.adapter.output.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titleGame;

    private String plataform;

    private String genre;

    private Double price;

    private Integer stock;
}
