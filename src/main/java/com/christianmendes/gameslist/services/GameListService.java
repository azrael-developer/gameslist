package com.christianmendes.gameslist.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.christianmendes.gameslist.repositories.GameListRepository;
import com.christianmendes.gameslist.entities.GameList;
import com.christianmendes.gameslist.dto.GameListDTO;

@Service
public class GameListService {

    @Autowired  
    private GameListRepository gameListRepository;

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
            List<GameList> result = gameListRepository.findAll();
            return result.stream().map(x -> new GameListDTO(x)).toList();
    }

}
