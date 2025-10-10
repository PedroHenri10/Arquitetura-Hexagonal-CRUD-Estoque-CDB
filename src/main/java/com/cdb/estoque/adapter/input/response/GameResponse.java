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

    @Schema(description = "ID do game", example = "1") Long id,
    @Schema(description = "Título do game", example = "Eocket League") String titleGame,
    @Schema(description = "Plataforma do game", example = "PC") String plataform,
    @Schema(description = "Gênero do game", example = "Esporte") String genre,
    @Schema(description = "Preço do game", example = "199.99") Double price,
    @Schema(description = "Quantidade em estoque", example = "25") Integer stock
}