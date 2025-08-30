package com.cdb.estoque.adapter.output.persistence;

import com.cdb.estoque.adapter.output.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataGameRepository extends JpaRepository<GameEntity, Long>{
}
