package com.cdb.estoque.adapter.output.persistence;

import com.cdb.estoque.adapter.output.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePersistenceAdapter extends JpaRepository<Game, Long> {
}
