package com.cdb.estoque.infrastructure;

import com.cdb.estoque.core.useCase.GameUseCase;
import com.cdb.estoque.port.input.GameInputPort;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameUseCaseBean {

    @Bean
    public GameInputPort gameInputPort(GameRepositoryPort gameRepositoryPort) {
        return new GameUseCase(gameRepositoryPort);
    }
}
