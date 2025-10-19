package com.cdb.estoque.adapter.input.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidGameRequest() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("PC")
                .genre("Action")
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Deve ser válido com todos os campos corretos");
    }

    @Test
    void testGameRequestWithNullTitleGame() {
        GameRequest request = GameRequest.builder()
                .plataform("PC")
                .genre("Action")
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O título do jogo é obrigatório.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithEmptyTitleGame() {
        GameRequest request = GameRequest.builder()
                .titleGame("")
                .plataform("PC")
                .genre("Action")
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O título do jogo é obrigatório.")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O título deve ter entre 2 e 100 caracteres.")));
    }

    @Test
    void testGameRequestWithShortTitleGame() {
        GameRequest request = GameRequest.builder()
                .titleGame("A")
                .plataform("PC")
                .genre("Action")
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O título deve ter entre 2 e 100 caracteres.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithLongTitleGame() {
        String longTitle = "A".repeat(101);
        GameRequest request = GameRequest.builder()
                .titleGame(longTitle)
                .plataform("PC")
                .genre("Action")
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O título deve ter entre 2 e 100 caracteres.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithNullPlataform() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .genre("Action")
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("A plataforma é obrigatória.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithEmptyPlataform() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("")
                .genre("Action")
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("A plataforma é obrigatória.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithLongPlataform() {
        String longPlataform = "A".repeat(51);
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform(longPlataform)
                .genre("Action")
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("A plataforma não deve exceder 50 caracteres.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithLongGenre() {
        String longGenre = "A".repeat(51);
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("PC")
                .genre(longGenre)
                .price(100.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O gênero não deve exceder 50 caracteres.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithNullPrice() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("PC")
                .genre("Action")
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O preço é obrigatório.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithNegativePrice() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("PC")
                .genre("Action")
                .price(-10.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O preço deve ser um valor positivo.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithZeroPrice() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("PC")
                .genre("Action")
                .price(0.0)
                .stock(10)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        // @Positive espera > 0. @PositiveOrZero espera >= 0. Price tem @Positive.
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O preço deve ser um valor positivo.", violations.iterator().next().getMessage());
    }


    @Test
    void testGameRequestWithNullStock() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("PC")
                .genre("Action")
                .price(100.0)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O estoque é obrigatório.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithNegativeStock() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("PC")
                .genre("Action")
                .price(100.0)
                .stock(-5)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O estoque não pode ser um número negativo.", violations.iterator().next().getMessage());
    }

    @Test
    void testGameRequestWithZeroStock() {
        GameRequest request = GameRequest.builder()
                .titleGame("Valid Game Title")
                .plataform("PC")
                .genre("Action")
                .price(100.0)
                .stock(0)
                .build();

        Set<ConstraintViolation<GameRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testLombokMethods() {
        GameRequest game1 = GameRequest.builder()
                .titleGame("Elden Ring")
                .plataform("PC")
                .genre("RPG")
                .price(199.90)
                .stock(10)
                .build();

        GameRequest game2 = GameRequest.builder()
                .titleGame("Elden Ring")
                .plataform("PC")
                .genre("RPG")
                .price(199.90)
                .stock(10)
                .build();

        assertEquals(game1, game2);
        assertEquals(game1.hashCode(), game2.hashCode());

        assertNotNull(game1.toString());
    }


}