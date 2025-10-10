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

    @Schema(description = "Identificador único do game.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Título completo do jogo.", example = "God of War Ragnarök")
    private String titleGame;

    @Schema(description = "Plataforma na qual o jogo está disponível.", example = "PlayStation 5")
    private String plataform;

    @Schema(description = "Gênero do jogo (ex: RPG, Ação, Aventura).", example = "Ação Aventura")
    private String genre;

    @Schema(description = "Preço de venda do jogo.", example = "299.90")
    private Double price;

    @Schema(description = "Quantidade atual do game em estoque.", example = "12")
    private Integer stock;
}