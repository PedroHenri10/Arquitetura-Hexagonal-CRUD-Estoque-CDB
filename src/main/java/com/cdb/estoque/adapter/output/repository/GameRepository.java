package com.cdb.estoque.adapter.output.repository;

import com.cdb.estoque.adapter.output.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
