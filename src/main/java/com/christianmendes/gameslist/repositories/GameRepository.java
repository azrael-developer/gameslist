package com.christianmendes.gameslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.christianmendes.gameslist.entities.Games;
public interface GameRepository extends JpaRepository<Games, Long> {

}
 