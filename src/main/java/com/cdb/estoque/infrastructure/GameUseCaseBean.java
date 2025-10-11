package com.cdb.estoque.infrastructure;

import com.cdb.estoque.core.domain.strategy.DecreaseStockOperation;
import com.cdb.estoque.core.domain.strategy.IncreaseStockOperation;
import com.cdb.estoque.core.domain.decorator.IdValidationGameUseCaseDecorator;
import com.cdb.estoque.core.domain.decorator.LoggingGameUseCaseDecorator;
import com.cdb.estoque.core.useCase.GameUseCase;
import com.cdb.estoque.port.input.GameInputPort;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class GameUseCaseBean {

    @Bean
    public GameUseCase gameCoreUseCase(GameRepositoryPort gameRepositoryPort,
                                       IncreaseStockOperation increaseStockOperation,
                                       DecreaseStockOperation decreaseStockOperation) {
        return new GameUseCase(gameRepositoryPort, increaseStockOperation, decreaseStockOperation);
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
    @Primary
    public GameInputPort gameInputPort(GameUseCase gameCoreUseCase) {
        GameInputPort useCaseWithIdValidation = new IdValidationGameUseCaseDecorator(gameCoreUseCase);
        return new LoggingGameUseCaseDecorator(useCaseWithIdValidation);
    }
}