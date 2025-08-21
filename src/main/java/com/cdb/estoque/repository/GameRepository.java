package com.cdb.estoque.repository;

import com.cdb.estoque.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
