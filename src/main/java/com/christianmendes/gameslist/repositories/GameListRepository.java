package com.christianmendes.gameslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.christianmendes.gameslist.entities.GameList;

public interface GameListRepository extends JpaRepository<GameList, Long> {

}
 