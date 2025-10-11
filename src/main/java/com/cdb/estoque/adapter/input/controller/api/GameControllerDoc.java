package com.cdb.estoque.adapter.input.controller.api;

import com.cdb.estoque.adapter.input.request.GameRequest;
import com.cdb.estoque.adapter.input.response.ErroResponse;
import com.cdb.estoque.adapter.input.response.GameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Games", description = "Endpoints para gerenciamento de games no estoque")
@RequestMapping("/games")
public interface GameControllerDoc {

    @Operation(summary = "Lista todos os games cadastrados",
            description = "Retorna uma lista completa de todos os games presentes no estoque.")
    @ApiResponse(responseCode = "200", description = "Lista de games retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "[{\"id\":1,\"titleGame\":\"The Witcher 3\",\"plataform\":\"PC\",\"genre\":\"RPG\",\"price\":150.00,\"stock\":5}]")
            )
    )
    @GetMapping
    ResponseEntity<List<GameResponse>> findAll();

    @Operation(summary = "Busca um game por ID",
            description = "Retorna os detalhes de um game específico com base no seu ID.")
    @ApiResponse(responseCode = "200", description = "Game encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "{\"id\":1,\"titleGame\":\"The Witcher 3\",\"plataform\":\"PC\",\"genre\":\"RPG\",\"price\":150.00,\"stock\":5}")
            )
    )
    @ApiResponse(responseCode = "404", description = "Game não encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2025-10-08T10:00:00.000+00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"GAME NOT FOUND\"}")
            )
    )
    @GetMapping("/{id}")
    ResponseEntity<GameResponse> findById(
            @Parameter(description = "ID do game a ser buscado", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Cria um novo game",
            description = "Adiciona um novo game ao estoque com título, plataforma, preço e quantidade inicial.")
    @ApiResponse(responseCode = "201", description = "Game criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "{\"id\":3,\"titleGame\":\"Battlefield 6\",\"plataform\":\"PC\",\"genre\":\"RPG\",\"price\":300.00,\"stock\":20}")
            )
    )
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2025-10-08T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"O título do jogo é obrigatório.\"}")
            )
    )
    @PostMapping
    ResponseEntity<GameResponse> createGame(
            @RequestBody(description = "Dados para criação de um novo game", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameRequest.class),
                            examples = @ExampleObject(value = "{\"titleGame\":\"Elden Ring\",\"plataform\":\"PC\",\"genre\":\"Action RPG\",\"price\":250.00,\"stock\":15}")
                    ))
            @Valid @org.springframework.web.bind.annotation.RequestBody GameRequest request);

    @Operation(summary = "Atualiza um game existente",
            description = "Atualiza todas as informações de um game específico com base no seu ID.")
    @ApiResponse(responseCode = "200", description = "Game atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "{\"id\":1,\"titleGame\":\"The Witcher 3: Wild Hunt (GOTY)\",\"plataform\":\"PC\",\"genre\":\"RPG\",\"price\":180.00,\"stock\":7}")
            )
    )
    @ApiResponse(responseCode = "404", description = "Game não encontrado para atualização",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2025-10-27T10:00:00.000+00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"GAME NOT FOUND\"}")
            )
    )
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2025-10-10T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"O preço deve ser um valor positivo.\"}")
            )
    )
    @PutMapping("/{id}")
    ResponseEntity<GameResponse> update(
            @Parameter(description = "ID do game a ser atualizado", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody(description = "Dados atualizados do game", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameRequest.class),
                            examples = @ExampleObject(value = "{\"titleGame\":\"The Witcher 3: Wild Hunt (GOTY)\",\"plataform\":\"PC\",\"genre\":\"RPG\",\"price\":180.00,\"stock\":7}")
                    ))
            @Valid @org.springframework.web.bind.annotation.RequestBody GameRequest request);

    @Operation(summary = "Aumenta o estoque de um game",
            description = "Adiciona uma determinada quantidade ao estoque de um game específico.")
    @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "{\"id\":1,\"titleGame\":\"The Witcher 3\",\"plataform\":\"PC\",\"genre\":\"RPG\",\"price\":150.00,\"stock\":10}")
            )
    )
    @ApiResponse(responseCode = "404", description = "Game não encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2025-10-09T10:00:00.000+00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"GAME NOT FOUND\"}")
            )
    )
    @ApiResponse(responseCode = "400", description = "Quantidade inválida (menor ou igual a zero)",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2025-10-10T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"A quantidade deve ser maior que zero\"}")
            )
    )
    @PatchMapping("/{id}/increase-stock")
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
                    examples = @ExampleObject(value = "{\"id\":1,\"titleGame\":\"The Witcher 3\",\"plataform\":\"PC\",\"genre\":\"RPG\",\"price\":150.00,\"stock\":2}")
            )
    )
    @ApiResponse(responseCode = "404", description = "Game não encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2025-10-07T10:00:00.000+00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"GAME NOT FOUND\"}")
            )
    )
    @ApiResponse(responseCode = "400", description = "Quantidade inválida (menor ou igual a zero ou maior que o estoque disponível)",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = {
                            @ExampleObject(name = "Quantidade Zero ou Negativa", value = "{\"timestamp\":\"2023-10-27T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"A quantidade deve ser maior que zero\"}"),
                            @ExampleObject(name = "Estoque Insuficiente", value = "{\"timestamp\":\"2025-10-10T10:00:00.000+00:00\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Estoque insuficiente para a quantidade solicitada\"}")
                    }
            )
    )
    @PatchMapping("/{id}/decrease-stock")
    ResponseEntity<GameResponse> decreaseStock(
            @Parameter(description = "ID do game para diminuir o estoque", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Quantidade a ser removida do estoque", required = true, example = "3")
            @RequestParam int quantity);

    @Operation(summary = "Exclui um game por ID",
            description = "Remove um game do estoque com base no seu ID.")
    @ApiResponse(responseCode = "204", description = "Game excluído com sucesso (No Content)")
    @ApiResponse(responseCode = "404", description = "Game não encontrado para exclusão",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponse.class),
                    examples = @ExampleObject(value = "{\"timestamp\":\"2025-10-10T10:00:00.000+00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"GAME NOT FOUND\"}")
            )
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID do game a ser excluído", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Busca games por título",
            description = "Retorna uma lista de games cujo título contém o texto especificado (busca case-insensitive).")
    @ApiResponse(responseCode = "200", description = "Lista de games encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "[{\"id\":1,\"titleGame\":\"The Witcher 3\",\"plataform\":\"PC\",\"genre\":\"RPG\",\"price\":150.00,\"stock\":5}]")
            )
    )
    @GetMapping("/search/title")
    ResponseEntity<List<GameResponse>> findByTitleGameContainingIgnoreCase(
            @Parameter(description = "Parte do título do game para busca", required = true, example = "witcher")
            @RequestParam String titleGame);

    @Operation(summary = "Busca games por gênero",
            description = "Retorna uma lista de games cujo gênero contém o texto especificado (busca case-insensitive).")
    @ApiResponse(responseCode = "200", description = "Lista de games encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "[{\"id\":4,\"titleGame\":\"Darks Souls\",\"plataform\":\"PC\",\"genre\":\"Action RPG\",\"price\":350.00,\"stock\":8}]")
            )
    )
    @GetMapping("/search/genre")
    ResponseEntity<List<GameResponse>> findByGenreContainingIgnoreCase(
            @Parameter(description = "Parte do gênero do game para busca", required = true, example = "rpg")
            @RequestParam String genre);

    @Operation(summary = "Busca games por plataforma",
            description = "Retorna uma lista de games cuja plataforma contém o texto especificado (busca case-insensitive).")
    @ApiResponse(responseCode = "200", description = "Lista de games encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameResponse.class),
                    examples = @ExampleObject(value = "[{\"id\":5,\"titleGame\":\"Horizon Forbidden West\",\"plataform\":\"PlayStation 5\",\"genre\":\"Action RPG\",\"price\":280.00,\"stock\":10}]")
            )
    )
    @GetMapping("/search/plataform")
    ResponseEntity<List<GameResponse>> findByPlataformContainingIgnoreCase(
            @Parameter(description = "Parte da plataforma do game para busca", required = true, example = "playstation")
            @RequestParam String plataform);
}