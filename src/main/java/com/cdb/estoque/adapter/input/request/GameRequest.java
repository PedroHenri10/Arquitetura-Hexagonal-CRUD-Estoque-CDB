// src/main/java/com/cdb/estoque/adapter/input/request/GameRequest.java
package com.cdb.estoque.adapter.input.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de entrada para criação ou atualização de um Game no estoque.")
public class GameRequest {

    @NotBlank(message = "O título do jogo é obrigatório.")
    @Size(min = 2, max = 100, message = "O título deve ter entre 2 e 100 caracteres.")
    @Schema(description = "Título completo do jogo.", example = "Final Fantasy VII Rebirth", required = true, minLength = 2, maxLength = 100)
    private String titleGame;

    @NotBlank(message = "A plataforma é obrigatória.")
    @Size(max = 50, message = "A plataforma não deve exceder 50 caracteres.")
    @Schema(description = "Plataforma na qual o jogo está disponível.", example = "PlayStation 5", required = true, maxLength = 50)
    private String plataform;

    @Size(max = 50, message = "O gênero não deve exceder 50 caracteres.")
    @Schema(description = "Gênero do jogo (ex: RPG, Ação, Aventura).", example = "RPG", maxLength = 50)
    private String genre;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser um valor positivo.")
    @Schema(description = "Preço de venda do jogo.", example = "299.90", required = true, format = "float")
    private Double price;

    @NotNull(message = "O estoque é obrigatório.")
    @PositiveOrZero(message = "O estoque não pode ser um número negativo.")
    @Schema(description = "Quantidade de unidades do jogo disponíveis em estoque.", example = "50", required = true, format = "int")
    private Integer stock;
}