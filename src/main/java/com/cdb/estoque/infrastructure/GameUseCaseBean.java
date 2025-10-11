package com.cdb.estoque.infrastructure;

import com.cdb.estoque.core.domain.factory.GameFactory;
import com.cdb.estoque.core.domain.strategy.DecreaseStockOperation;
import com.cdb.estoque.core.domain.strategy.IncreaseStockOperation;
import com.cdb.estoque.core.useCase.GameUseCase;
import com.cdb.estoque.port.input.GameInputPort;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameUseCaseBean {

    @Bean
    public GameInputPort gameInputPort(GameRepositoryPort gameRepositoryPort,
                                       IncreaseStockOperation increaseStockOperation,
                                       DecreaseStockOperation decreaseStockOperation,
                                       GameFactory gameFactory) {
        return new GameUseCase(gameRepositoryPort, increaseStockOperation, decreaseStockOperation, gameFactory);
    }

    @Bean
    public IncreaseStockOperation increaseStockOperation() {
        return new IncreaseStockOperation();
    }

    @Bean
    public DecreaseStockOperation decreaseStockOperation() {
        return new DecreaseStockOperation();
    }

    @Bean
    public GameFactory gameFactory() { 
        return new GameFactory();
    }
}
