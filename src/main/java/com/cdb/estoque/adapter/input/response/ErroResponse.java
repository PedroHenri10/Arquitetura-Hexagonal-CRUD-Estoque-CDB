package com.cdb.estoque.adapter.input.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa uma estrutura padronizada para respostas de erro da API.")
public class ErroResponse {

    @Schema(description = "Timestamp da ocorrência do erro.", example = "2023-10-27T10:30:00.123456", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP do erro.", example = "404", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer status;

    @Schema(description = "Tipo de erro HTTP.", example = "Not Found", accessMode = Schema.AccessMode.READ_ONLY)
    private String error;

    @Schema(description = "Mensagem detalhada sobre o erro.", example = "Game com ID 123 não encontrado.", accessMode = Schema.AccessMode.READ_ONLY)
    private String message;

    @Schema(description = "Caminho da requisição que gerou o erro.", example = "/api/games/123", accessMode = Schema.AccessMode.READ_ONLY)
    private String path;
}