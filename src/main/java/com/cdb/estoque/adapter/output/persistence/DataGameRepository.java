package com.cdb.estoque.adapter.output.persistence;

import com.cdb.estoque.adapter.output.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataGameRepository extends JpaRepository<GameEntity, Long>{


    List<GameEntity> findByTitle(String titleGame);
    List<GameEntity> findByGenre(String genre);
}
