package com.cdb.estoque.adapter.input.controller.api;

import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.adapter.input.response.ErroResponse;
import com.cdb.estoque.adapter.input.response.GameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Games", description = "Endpoints para gerenciamento de games no estoque")
@RequestMapping("/api/games")
public interface GameControllerDoc {

    @Operation(summary = "Lista todos os games cadastrados",
            description = "Retorna uma lista completa de todos os games presentes no estoque.")
    @ApiResponse(responseCode = "200", description = "Lista de games retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "[{\"id\":1,\"title\":\"The Witcher 3\",\"stock\":5},{\"id\":2,\"title\":\"Cyberpunk 2077\",\"stock\":10}]")
            )
    )
    @GetMapping
    ResponseEntity<List<GameResponse>> findAll();

    @Operation(summary = "Busca um game por ID",
            description = "Retorna os detalhes de um game específico com base no seu ID.")
    @ApiResponse(responseCode = "200", description = "Game encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "{\"id\":1,\"title\":\"The Witcher 3\",\"stock\":5}")
            )
    )
    @ApiResponse(responseCode = "404", description = "Game não encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2023-10-27T10:00:00.000+00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Game com ID 10 não encontrado\"}")
            )
    )
    @GetMapping("/{id}")
    ResponseEntity<GameResponse> findById(
            @Parameter(description = "ID do game a ser buscado", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Cria um novo game",
            description = "Adiciona um novo game ao estoque com título e quantidade inicial.")
    @ApiResponse(responseCode = "201", description = "Game criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "{\"id\":3,\"title\":\"Starfield\",\"stock\":20}")
            )
    )
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2023-10-27T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Título é obrigatório\"}")
            )
    )
    @PostMapping
    ResponseEntity<GameResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para criação de um novo game", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameRequest.class),
                            examples = @ExampleObject(value = "{\"title\":\"Elden Ring\",\"stock\":15}")
                    ))
            @RequestBody GameRequest request);

    @Operation(summary = "Aumenta o estoque de um game",
            description = "Adiciona uma determinada quantidade ao estoque de um game específico.")
    @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "{\"id\":1,\"title\":\"The Witcher 3\",\"stock\":10}")
            )
    )
    @ApiResponse(responseCode = "404", description = "Game não encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2023-10-27T10:00:00.000+00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Game com ID 100 não encontrado\"}")
            )
    )
    @ApiResponse(responseCode = "400", description = "Quantidade inválida (menor ou igual a zero)",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2023-10-27T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"A quantidade deve ser maior que zero\"}")
            )
    )
    @PutMapping("/{id}/increase")
    ResponseEntity<GameResponse> increaseStock(
            @Parameter(description = "ID do game para aumentar o estoque", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Quantidade a ser adicionada ao estoque", required = true, example = "5")
            @RequestParam int quantity);

    @Operation(summary = "Diminui o estoque de um game",
            description = "Remove uma determinada quantidade do estoque de um game específico.")
    @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "{\"id\":1,\"title\":\"The Witcher 3\",\"stock\":2}")
            )
    )
    @ApiResponse(responseCode = "404", description = "Game não encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2023-10-27T10:00:00.000+00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Game com ID 100 não encontrado\"}")
            )
    )
    @ApiResponse(responseCode = "400", description = "Quantidade inválida (menor ou igual a zero ou maior que o estoque disponível)",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = {
                            @ExampleObject(name = "Quantidade Zero ou Negativa", value = "{\"timestamp\":\"2023-10-27T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"A quantidade deve ser maior que zero\"}"),
                            @ExampleObject(name = "Estoque Insuficiente", value = "{\"timestamp\":\"2023-10-27T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Estoque insuficiente para a quantidade solicitada\"}")
                    }
            )
    )
    @PutMapping("/{id}/decrease")
    ResponseEntity<GameResponse> decreaseStock(
            @Parameter(description = "ID do game para diminuir o estoque", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Quantidade a ser removida do estoque", required = true, example = "3")
            @RequestParam int quantity);
}