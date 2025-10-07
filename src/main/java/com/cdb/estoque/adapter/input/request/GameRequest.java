package com.cdb.estoque.adapter.input.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;


/**
 * DTO (Data Transfer Object) de entrada para criação ou atualização de Game.
 *
 * 📌 Observações:
 *  Pode usar Lombok para reduzir boilerplate (getters, construtores, toString).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}