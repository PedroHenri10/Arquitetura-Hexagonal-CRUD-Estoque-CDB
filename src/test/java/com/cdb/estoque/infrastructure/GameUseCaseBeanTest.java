package com.cdb.estoque.infrastructure;

import com.cdb.estoque.core.domain.strategy.DecreaseStockOperation;
import com.cdb.estoque.core.domain.strategy.IncreaseStockOperation;
import com.cdb.estoque.core.domain.decorator.IdValidationGameUseCaseDecorator;
import com.cdb.estoque.core.domain.decorator.LoggingGameUseCaseDecorator;
import com.cdb.estoque.core.useCase.GameUseCase;
import com.cdb.estoque.port.input.GameInputPort;
import com.cdb.estoque.port.output.GameRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GameUseCaseBeanTest {

    private GameUseCaseBean bean;
    private GameRepositoryPort repo;

    @BeforeEach
    void setUp() {
        bean = new GameUseCaseBean();
        repo = mock(GameRepositoryPort.class);
    }

    @Test
    void deveCriarBeansSemErros() {
        IncreaseStockOperation inc = bean.increaseStockOperation();
        DecreaseStockOperation dec = bean.decreaseStockOperation();
        GameUseCase useCase = bean.gameCoreUseCase(repo, inc, dec);
        GameInputPort port = bean.gameInputPort(useCase);

        assertThat(inc).isNotNull();
        assertThat(dec).isNotNull();
        assertThat(useCase).isNotNull();
        assertThat(port).isNotNull();
        assertThat(port).isInstanceOf(LoggingGameUseCaseDecorator.class);

        assertThat(port.toString()).contains("LoggingGameUseCaseDecorator");
    }
}
