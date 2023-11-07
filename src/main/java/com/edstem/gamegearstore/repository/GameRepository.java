package com.edstem.gamegearstore.repository;

import com.edstem.gamegearstore.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {}
