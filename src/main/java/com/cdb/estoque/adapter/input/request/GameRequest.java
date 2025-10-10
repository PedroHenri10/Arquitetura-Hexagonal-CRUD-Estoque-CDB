// src/main/java/com/cdb/estoque/adapter/input/request/GameRequest.java
package com.cdb.estoque.adapter.input.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa os dados de entrada para criação ou atualização de um game.")
public class GameRequest {
    @Schema(description = "Título do game", example = "Rocket League", required = true)
    private String titleGame;

    @Schema(description = "Plataforma do game", example = "PC", required = true)
    private String plataform;

    @Schema(description = "Gênero do game", example = "Esporte")
    private String genre;

    @Schema(description = "Preço do game", example = "199.99", required = true)
    private Double price;

    @Schema(description = "Quantidade em estoque", example = "25", required = true)
    private Integer stock;
}
