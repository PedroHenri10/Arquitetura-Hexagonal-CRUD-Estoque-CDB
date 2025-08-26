package com.cdb.estoque.output.repository.repository;

import com.cdb.estoque.output.repository.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
