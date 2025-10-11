package com.cdb.estoque.core.domain.decorator;

import com.cdb.estoque.core.domain.model.Game;
import com.cdb.estoque.port.input.GameInputPort;

import java.util.List;
import java.util.Optional;

public class IdValidationGameUseCaseDecorator implements GameInputPort{

        private final GameInputPort decoratedGameUseCase;

        public IdValidationGameUseCaseDecorator(GameInputPort decoratedGameService) {
            this.decoratedGameUseCase = decoratedGameService;
        }

        private void validateId(Long id) {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("The game ID shoud be positive value and not null.");
            }
        }

        @Override
        public List<Game> findAll() {
            return decoratedGameUseCase.findAll();
        }

        @Override
        public Optional<Game> findById(Long id) {
            validateId(id);
            return decoratedGameUseCase.findById(id);
        }

        @Override
        public Game save(Game game) {
            return decoratedGameUseCase.save(game);
        }

        @Override
        public Game update(Long id, Game game) {
            validateId(id);
            return decoratedGameUseCase.update(id, game);
        }

        @Override
        public Game increaseStock(Long id, int quantity) {
            validateId(id);
            return decoratedGameUseCase.increaseStock(id, quantity);
        }

        @Override
        public Game decreaseStock(Long id, int quantity) {
            validateId(id);
            return decoratedGameUseCase.decreaseStock(id, quantity);
        }

        @Override
        public List<Game> findByTitleGameContainingIgnoreCase(String titleGame) {
            return decoratedGameUseCase.findByTitleGameContainingIgnoreCase(titleGame);
        }

        @Override
        public List<Game> findByGenreContainingIgnoreCase(String genre) {
            return decoratedGameUseCase.findByGenreContainingIgnoreCase(genre);
        }

        @Override
        public List<Game> findByPlataformContainingIgnoreCase(String plataform) {
            return decoratedGameUseCase.findByPlataformContainingIgnoreCase(plataform);
        }

        @Override
        public void deleteById(Long id) {
            validateId(id);
            decoratedGameUseCase.deleteById(id);
        }
}
