package com.cdb.estoque.adapter.input.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa os dados de um game retornado pela API.")
public class GameResponse {

    @Schema(description = "ID do game", example = "1")
    private Long id;

    @Schema(description = "Título do game", example = "Rocket League")
    private String titleGame;

    @Schema(description = "Plataforma do game", example = "PC")
    private String plataform;

    @Schema(description = "Gênero do game", example = "Esporte")
    private String genre;

    @Schema(description = "Preço do game", example = "199.99")
    private Double price;

    @Schema(description = "Quantidade em estoque", example = "25")
    private Integer stock;
}